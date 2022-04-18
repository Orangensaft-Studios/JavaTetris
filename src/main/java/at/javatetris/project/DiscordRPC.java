package at.javatetris.project;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;

/**
 * class to start and update Discord Rich Presence
 * @author Severin Rosner
 */
public class DiscordRPC {

    /** discord presence */
    private static DiscordRichPresence presence = new DiscordRichPresence();

    /** instance */
    private static club.minnced.discord.rpc.DiscordRPC lib = club.minnced.discord.rpc.DiscordRPC.INSTANCE;

    /** start the rpc */
    public static void startPresence() {

        String applicationId = "964554055420624947";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("DiscordRPC.java: Started DC-RPC");

        lib.Discord_Initialize(applicationId, handlers, true, steamId);

        presence.state = Language.getPhrase("dcStarting");

        presence.details = "v." + Settings.searchSettings("gameVersion");

        lib.Discord_UpdatePresence(presence);

        final int sleepTime = 2000;
        // in a worker thread
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ignored) {
                }
            }
        }, "RPC-Callback-Handler").start();
    }

    /**
     * update state of rpc
     * @param state new state
     */
    public static void updateStateRPC(String state) {
        presence.state = state;
        lib.Discord_UpdatePresence(presence);
    }

    /**
     * update rpc
     * @param details new details
     * @param state new state
     */
    public static void updateRPC(String details, String state) {
        presence.state = state;
        presence.details = details;
        lib.Discord_UpdatePresence(presence);
    }

    /**
     * update details of rpc
     * @param details new details
     */
    public static void updateDetailsRPC(String details) {
        presence.details = details;
        lib.Discord_UpdatePresence(presence);
    }

    /*
    public static void startTimeRPC() {
        presence.startTimestamp = System.currentTimeMillis() / 1000;
    }

    public static void endTimeRPC(long endTime) {
        presence.startTimestamp = endTime;
    }

     */

}
