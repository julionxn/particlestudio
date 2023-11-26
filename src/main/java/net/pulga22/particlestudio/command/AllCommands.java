package net.pulga22.particlestudio.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class AllCommands {

    public static void register(){
        CommandRegistrationCallback.EVENT.register(ToggleEditingModeCommand::register);
    }

}
