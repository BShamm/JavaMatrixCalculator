package muttlab.operator;

import muttlab.operator.basic.UNKNOWNoperator;
import muttlab.operator.basic.ADDoperator;
import muttlab.operator.basic.SUBoperator;
import muttlab.operator.basic.NEWoperator;
import muttlab.operator.basic.MULTIPLYoperator;
import muttlab.operator.basic.DUPoperator;
import muttlab.operator.basic.MULTIPLYELEMENTWISEoperator;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author bens
 */

public class CommandFactory_static_map
{
    // a constant map that holds all valid command words
    private static final Map<String, Class<? extends Command>> validCommands = new HashMap<>();
    static {
        validCommands.put("[", NEWoperator.class);
        validCommands.put("+", ADDoperator.class);
        validCommands.put("-", SUBoperator.class);
        validCommands.put("*", MULTIPLYoperator.class);
        validCommands.put(".*", MULTIPLYELEMENTWISEoperator.class);
        validCommands.put("dup", DUPoperator.class);
    }

    /**
     * Check whether a given String is a valid command word. 
     * @param aString the string
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public static boolean isCommand(String aString) { return validCommands.containsKey(aString); }
    
    public static Command newCommand(String word1, String rest) {
        Class<? extends Command> cmd = validCommands.get(word1);
        if (cmd == null) cmd = UNKNOWNoperator.class;
        Class[] args = {String.class, String.class};
        try {
            return cmd.getDeclaredConstructor(args).newInstance(word1, rest);
        } catch (NoSuchMethodException|IllegalArgumentException|InvocationTargetException|InstantiationException ex) {
            Logger.getLogger(CommandFactory_static_map.class.getName()).log(Level.SEVERE, "no constructor for "+"word1", ex);
        } catch (SecurityException|IllegalAccessException ex) {
            Logger.getLogger(CommandFactory_static_map.class.getName()).log(Level.SEVERE, "security exception", ex);
        }
        return null;
    }
}
