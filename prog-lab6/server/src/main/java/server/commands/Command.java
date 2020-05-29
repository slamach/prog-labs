package server.commands;

/**
 * Interface for all commands.
 */
public interface Command {
    String getName();

    String getUsage();

    String getDescription();

    boolean execute(String commandStringArgument, Object commandObjectArgument);
}
