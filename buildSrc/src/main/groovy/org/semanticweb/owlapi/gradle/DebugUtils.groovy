package org.semanticweb.owlapi.gradle

import java.lang.reflect.Field

public class DebugUtils {
    static def getP(Object pss, String fieldName) {
        Object c = getPrivateField(pss, pss.getClass(), fieldName)
        return c
    }

    static Object getPrivateField(Object o, Class klass, String fieldName) {
        Field f = klass.getDeclaredFields().find({ it.name == fieldName })
        if (f == null) {
            return getPrivateField(o, klass.getSuperclass(), fieldName)
        } else {
            f.setAccessible(true)
            def c = f.get(o)
            f.setAccessible(false)
            return c
        }
    }

   static  def dumpObjectFields(
            def o, Class klass = o.class, String path = "", StringBuilder buf = new StringBuilder(),
            def seen = new IdentityHashMap(), int depth = 0) {
        if (depth > 5) {
            return buf
        }
        if (klass == null || klass == Object.class) {
            return buf
        }


        Field[] fields = klass.declaredFields
        fields.each { field ->

            def savedAccess = field.accessible
            if (!savedAccess) {
                field.accessible = true
            }
            def v
            v = field.get(o)
            if (!savedAccess) {
                field.accessible = false
            }

            try {
                buf.append(path)
                buf.append(field.name)
                buf.append '='
                buf.append String.valueOf(v)
                buf.append "\n"
            } catch (Exception e) {
                println("can't build ref", e)
            }

            if (v != null && field.type != Class.class && !field.type.isPrimitive() && field.name != "__meta_class__"
                    && field.name != "__dyn_obj__" && field.name != 'metaClass'
            ) {
                def add = seen.put(v, v) == null

                if (add) {
                    dumpObjectFields(v, v.getClass(), "${path}${field.name}.", buf, seen, depth + 1)
                }
            }
        }
        if (klass != Object.class) {
            dumpObjectFields(o, klass.superclass, path, buf, seen, depth)
        }

        return buf
    }
}
