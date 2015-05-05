package de.fips.common.lang;

public final class Base62 {

    private Base62() {
    }

    public static String toHex(String base62String) {
        return BaseConvert.convert(base62String, 62, 16);
    }

    public static String fromHex(String hexString) {
        return BaseConvert.convert(hexString, 16, 62);
    }
}
