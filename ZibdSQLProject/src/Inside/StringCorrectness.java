package Inside;

public class StringCorrectness {
    public static boolean isInt(String str){
        try{
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
    public static boolean isFloat(String str){
        try{
            Float.parseFloat(str);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
    public static boolean isDouble(String str){
        try{
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
}
