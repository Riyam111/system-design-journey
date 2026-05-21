

/*Build a text formatting system where different strategies format text in different ways. The TextEditor context should allow swapping formatters at runtime, so the same editor can produce uppercase, lowercase, or title case output depending on the active strategy.

Requirements:

Strategy interface: TextFormatter with a
method format(text) that returns a formatted string
Concrete strategies: UpperCaseFormatter, LowerCaseFormatter, TitleCaseFormatter
Context: TextEditor with setFormatter() to swap strategies and publishText() to format and print text*/
// Strategy Interface
interface TextFormatter{
    String format(String text);
}
// Concrete Strategy for Upper Case
class uppercaseformatter implements TextFormatter{
    @Override
    public String format(String text){
        return text.toUpperCase();
    }
}
// Concrete Strategy for Lower Case
class lowercaseformatter implements TextFormatter{
    @Override
    public String format(String text){
        return text.toLowerCase();
    }
}
// Concrete Strategy for Title Case
class titlecaseformatter implements TextFormatter{
    @Override
    public String format(String text){
        String[] words = text.split(" ");
        StringBuilder titleCase = new StringBuilder();
        for(String word : words){
            if(word.length() > 0){
                titleCase.append(Character.toUpperCase(word.charAt(0)))
                         .append(word.substring(1).toLowerCase())
                         .append(" ");
            }
        }
        return titleCase.toString().trim();
    }
}
// Context
class TextEditoris{
    private TextFormatter formatter;
    public TextEditoris (TextFormatter formatter){
        this.formatter = formatter;
    }
    public void setFormatter(TextFormatter formatter){
        this.formatter = formatter;
    }
    public void publishText(String text){
        String formattedText = formatter.format(text);
        System.out.println(formattedText);
    }
}
// Main class to test the implementation
public class Texteditor {
    public static void main(String[] args) {
        TextEditoris editor = new TextEditoris(new uppercaseformatter());
        editor.publishText("hello world"); // Output: HELLO WORLD

        editor.setFormatter(new lowercaseformatter());
        editor.publishText("HELLO WORLD"); // Output: hello world

        editor.setFormatter(new titlecaseformatter());
        editor.publishText("hello world"); // Output: Hello World
    }
}
