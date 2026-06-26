package android.support.persistent.cache.sharepreferences

import android.content.Context
import android.support.persistent.Parser
import android.support.persistent.cache.GsonParser
import com.google.gson.Gson
import java.lang.reflect.Type

class GsonCaching(
    context: Context,
) : Caching(context, GsonParser(), DefaultSharePreferencesFactory())