package kr.madesv.extension.skript;

import com.google.inject.Injector;

public abstract class SkriptRegistrarModule {
    final public void register(Injector injector) {
        injector.injectMembers(this);
        register();
    }
    public abstract void register();
}

