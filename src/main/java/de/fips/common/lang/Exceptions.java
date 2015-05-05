package de.fips.common.lang;

public final class Exceptions {

    private Exceptions() {
    }

    public static RuntimeException sneakyThrow(Throwable t) {
        if (t == null) throw new NullPointerException("t");
        Exceptions.<RuntimeException>sneakyThrowImpl(t);
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void sneakyThrowImpl(Throwable t) throws T {
        throw (T) t;
    }
}
