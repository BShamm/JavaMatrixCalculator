package muttlab.operator;

import muttlab.MuttLab;

/**
 * 
 * @author bens
 */

public abstract class Command
{
    private final String commandWord;
    private final String secondWord;
    public static String NAME;

    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised.
     * @param secondWord The second word of the command.
     */
    public Command(String firstWord, String secondWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public String getCommandWord() { return commandWord; }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord() { return secondWord; }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord() { return (secondWord != null); }
    
    /**
     * Execute this command
     * @param muttlab the MuttLab work bench
     * @return false unless this is the quit command
     */
    public abstract boolean execute(MuttLab muttlab);
    
    
}

