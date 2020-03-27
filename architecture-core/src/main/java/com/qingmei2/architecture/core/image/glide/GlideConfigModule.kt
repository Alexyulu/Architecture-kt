package com.qingmei2.architecture.core.image.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.qingmei2.architecture.core.image.glide.progress.ProgressEngine
import java.io.InputStream

@GlideModule
class GlideConfigModule : AppGlideModule() {

    /**
     * Using the @GlideModule annotation requires a dependency on Glide’s annotations:
     */
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        /**
         * 替换Glide的请求方式，因为要监听图片的加载进度，使用okhttp来加载
         */
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(ProgressEngine.getOkHttpClient()))
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDiskCache(InternalCacheDiskCacheFactory(context,
                diskCacheFolderName(context),
                diskCacheSizeBytes()))
                .setMemoryCache(LruResourceCache(memoryCacheSizeBytes().toLong()))
    }

    /**
     * Implementations should return `false` after they and their dependencies have migrated
     * to Glide's annotation processor.
     */
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    /**
     * set the memory cache size, unit is the [Byte].
     */
    private fun memoryCacheSizeBytes(): Int {
        return 1024 * 1024 * 20 // 20 MB
    }

    /**
     * set the disk cache size, unit is the [Byte].
     */
    private fun diskCacheSizeBytes(): Long {
        return 1024 * 1024 * 512 // 512 MB
    }

    /**
     * set the disk cache folder's name.
     */
    private fun diskCacheFolderName(context: Context): String {
        return "akt"
    }
}