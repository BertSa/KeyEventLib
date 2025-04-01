package ca.bertsa.keyeventlib.client;

import ca.bertsa.keyeventlib.KeyEventHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.HashMap;
import java.util.function.Function;

public class KeyEventLibClient implements ClientModInitializer {
    private static final HashMap<String, KeyEventHandler> HANDLER_HASH_MAP = new HashMap<>();
    private static final HashMap<String, Function<KeyEventHandler, Void>> FUNCTION_HASH_MAP = new HashMap<>();

    @Override
    public void onInitializeClient() {
        registerEatingKeyPressedEvent();
    }

    private void registerEatingKeyPressedEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> HANDLER_HASH_MAP.forEach((s, keyBinding) -> FUNCTION_HASH_MAP.get(s).apply(keyBinding)));
    }

    public static boolean setSomething(String id, KeyEventHandler keyEventHandler, Function<KeyEventHandler, Void> function) {
        if (HANDLER_HASH_MAP.containsKey(id) || FUNCTION_HASH_MAP.containsKey(id)) {
            return false;
        }
        HANDLER_HASH_MAP.put(id, keyEventHandler);
        FUNCTION_HASH_MAP.put(id, function);

        return true;
    }
}

