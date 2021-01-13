package com.damein;

/**
 * Created by liyang on 2018/1/29
 * 引用数据对象，转换成基本数据类型
 */
public abstract class GV {

    private GV() {
    }

    public static boolean z(Object value) {
        return z(value, false);
    }

    public static boolean z(Object value, boolean defaultValue) {
        if (value != null) {
            if (value instanceof Boolean) {
                return ((Boolean) value).booleanValue();
            }
            // 1: true, other: false
            final String v = value.toString().toLowerCase();
            if ("true".equals(v) || "1".equals(v)) {
                return true;
            }
            if ("false".equals(v) || "0".equals(v)) {
                return false;
            }
        }
        return defaultValue;
    }

    public static byte b(Object value) {
        return b(value, (byte) 0);
    }

    public static byte b(Object value, byte defaultValue) {
        if (value != null) {
            if (value instanceof Byte) {
                return ((Byte) value).byteValue();
            }
            if (value instanceof Number) {
                return ((Number) value).byteValue();
            }
            try {
                return Byte.parseByte(value.toString());
            } catch (final Exception e) {
            }
        }
        return defaultValue;
    }

    public static char c(Object value) {
        return c(value, '\000');
    }

    public static char c(Object value, char defaultValue) {
        if (value != null) {
            if (value instanceof Character) {
                return ((Character) value).charValue();
            }
            if (value instanceof CharSequence && !value.toString().isEmpty()) {
                return ((CharSequence) value).charAt(0);
            }
            if (value instanceof Number) {
                return (char) ((Number) value).intValue();
            }
        }
        return defaultValue;
    }

    public static short s(Object value) {
        return s(value, (short) 0);
    }

    public static short s(Object value, short defaultValue) {
        if (value != null) {
            if (value instanceof Short) {
                return ((Short) value).shortValue();
            }
            if (value instanceof Number) {
                return ((Number) value).shortValue();
            }
            try {
                return Short.parseShort(value.toString());
            } catch (final Exception e) {
            }
        }
        return defaultValue;
    }

    public static int i(Object value) {
        return i(value, 0);
    }

    public static int i(Object value, int defaultValue) {
        if (value != null) {
            if (value instanceof Integer) {
                return ((Integer) value).intValue();
            }
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
            try {
                return Integer.parseInt(value.toString());
            } catch (final Exception e) {
                if (value instanceof Boolean) {
                    return (Boolean) value ? 1 : 0;
                }
            }
        }
        return defaultValue;
    }

    public static long l(Object value) {
        return l(value, 0L);
    }

    public static long l(Object value, long defaultValue) {
        if (value != null) {
            if (value instanceof Long) {
                return ((Long) value).longValue();
            }
            if (value instanceof Number) {
                return ((Number) value).longValue();
            }
            try {
                return Long.parseLong(value.toString());
            } catch (final Exception e) {
            }
        }
        return defaultValue;
    }

    public static float f(Object value) {
        return f(value, 0F);
    }

    public static float f(Object value, float defaultValue) {
        if (value != null) {
            if (value instanceof Float) {
                return ((Float) value).floatValue();
            }
            if (value instanceof Number) {
                return ((Number) value).floatValue();
            }
            try {
                return Float.parseFloat(value.toString());
            } catch (final Exception e) {
            }
        }
        return defaultValue;
    }

    public static double d(Object value) {
        return d(value, 0D);
    }

    public static double d(Object value, double defaultValue) {
        if (value != null) {
            if (value instanceof Double) {
                return ((Double) value).doubleValue();
            }
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }
            try {
                return Double.parseDouble(value.toString());
            } catch (final Exception e) {
            }
        }
        return defaultValue;
    }

    public static String str(Object value) {
        return str(value, "");
    }

    public static String str(Object value, String defaultValue) {
        if (value != null) {
            if (value instanceof String) {
                return (value).toString();
            }
            try {
                return value.toString();
            } catch (final Exception e) {
            }
        }
        return defaultValue;
    }
}
