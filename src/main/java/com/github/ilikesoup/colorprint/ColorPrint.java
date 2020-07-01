package main.java.com.github.ilikesoup.colorprint;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 带颜色的输出，让(gei)你(ta)的(men)生(yi)活(dian)多(yan)彩(se)多(kan)姿(kan)。
 * 请使用帮助类 ColorPrintHelp 快速了解 ColorPrint
 * @see ColorPrintHelp
 */
public class ColorPrint {
    /**
     * 核心就是这句话，懂了就可以自己耍
     * printf("\033[颜色;背景;控制码m文本\033[控制码m")
     * 控制码: 前面的控制码控制此次的样式，后面的控制码用于重置，以防影响之后的输出，通常设置为 0
     */
    public static final String PRINT_TEMPLATE = "\033[%d;%d;%dm%s\033[0m";

    private static PrintStream GLOBAL_PRINT_STREAM;
    public static final PrintStream DEFAULT_GLOBAL_PRINT_STREAM = System.out;

    public static PrintStream getGlobalPrintStream() {
        return GLOBAL_PRINT_STREAM == null ? DEFAULT_GLOBAL_PRINT_STREAM : GLOBAL_PRINT_STREAM;
    }

    public static void setGlobalPrintStream(PrintStream printStream) {
        GLOBAL_PRINT_STREAM = printStream;
    }

    /**
     * 每个人的心中都有一道彩虹。LOVE IS LOVE。
     */
    public static final ColorPrint RAINBOW = new ColorPrint(ColorStyle.ofList(Color.rainbow()));

    /**
     * 粗粗粗
     */
    public static final ColorPrint RAINBOW_BOLD = new ColorPrint(ColorStyle.ofList(Style.BOLD, Color.rainbow()));
    /*
     * 给你，你想要的 RGB
     */
    public static final ColorPrint RED = new ColorPrint(ColorStyle.ofList(Color.RED));
    public static final ColorPrint GREEN = new ColorPrint(ColorStyle.ofList(Color.GREEN));
    public static final ColorPrint BLUE = new ColorPrint(ColorStyle.ofList(Color.BLUE));
    /**
     * 样式列表
     */
    private final List<ColorStyle> styles;

    private PrintStream printStream;

    /**
     * 假装看不见
     * @param styles
     */
    private ColorPrint(List<ColorStyle> styles) {
        this(styles, getGlobalPrintStream());
    }

    private ColorPrint(List<ColorStyle> styles, PrintStream printStream) {
        this.styles = styles;
        this.printStream = printStream;
    }

    /*
     * 成员方法，循环使用样式列表中的样式，支持连续打印
     */

    /**
     * 根据颜色样式列表，给每个对象依次上色并打印
     * @param contents
     * @return
     */
    public ColorPrint print(Object... contents) {
        if(contents == null) {
            return this;
        }
        for (int i = 0; i < contents.length; i++) {
            print0(printStream, styles.get(i % styles.size()), contents[i]);
        }
        return this;
    }

    /**
     * 根据颜色样式列表，给每个对象依次上色、打印，每个对象打印完后会换行
     * @param contents
     * @return
     */
    public ColorPrint println(Object... contents) {
        if(contents == null) {
            printStream.println();
            return this;
        }
        for (int i = 0; i < contents.length; i++) {
            println0(printStream, styles.get(i % styles.size()), contents[i]);
        }
        return this;
    }

    /**
     * 只对正则比配的内容进行上色打印，每个对象匹配内容的颜色，会根据提供的颜色样式列表依次变化
     * @param regex
     * @param contents
     * @return
     */
    public ColorPrint printr(String regex, Object... contents) {
        if(contents == null) {
            return this;
        }
        Pattern pattern = Pattern.compile(regex);
        for (int i = 0; i < contents.length; i++) {
            colorRegex(printStream, styles.get(i % styles.size()), pattern, Objects.toString(contents[i]));
        }
        return this;
    }

    /**
     * 只对正则匹配的内容进行上色打印，并且根据提供的颜色样式列表，对每个不同的分组标记不同的颜色
     * 对于这种情况，应至少提供两种颜色，由于子分组需要使用和全匹配不同的颜色
     * @param regex
     * @param contents
     * @return
     */
    public ColorPrint printg(String regex, Object... contents) {
        Pattern pattern = Pattern.compile(regex);
        for (int i = 0; i < contents.length; i++) {
            colorGroup(printStream, styles, pattern, Objects.toString(contents[i]));
        }
        return this;
    }

    public ColorPrint printa(Object... contents) {
        printg(AUTO_PRINT_REGEX, contents);
        return this;
    }

    /**
     * 用不同的颜色打印多次
     * @param source
     * @param times
     * @return
     */
    public ColorPrint printm(Object source, int times) {
        printm(source, times, styles.toArray(new ColorStyle[]{}));
        return this;
    }

    /*
     * 静态工厂方法，构造自定义颜色样式
     * 别说我没给你 DIY 的机会
     */

    /**
     * 通过颜色列表构建，用一些自己喜欢的颜色分别给不同的内容上色
     * 如果难得思考或不想选择，可以直接使用 RAINBOW, 它包含了所有的彩色
     * @param colors
     * @return
     */
    public static ColorPrint of(Color... colors) {
        return new ColorPrint(ColorStyle.ofList(colors));
    }

    /**
     * 通过颜色列表构建，用一些自己喜欢的颜色分别给不同的内容上色
     * 该选项提供了样式，你不仅可以对文字进行上色，还可以使它们加粗，倾斜...
     * 只是这种样式，会影响所有的颜色。
     * @param style
     * @param colors
     * @return
     */
    public static ColorPrint of(Style style, Color... colors) {
        return new ColorPrint(ColorStyle.ofList(style, colors));
    }

    /**
     * 最灵活的一种，你自己看着办
     * @param styles
     * @return
     */
    public static ColorPrint of(ColorStyle... styles) {
        return new ColorPrint(Arrays.asList(styles));
    }

    /*
     * 静态方法
     */

    /**
     * 给一些内容上色
     * @param font
     * @param contents
     */
    public static void print(Color font, Object... contents) {
        print(ColorStyle.of(font), contents);
    }

    /**
     * 给一些内容上色，加样式
     * @param font
     * @param style
     * @param contents
     */
    public static void print(Color font, Style style, Object... contents) {
        print(ColorStyle.of(font, style), contents);
    }

    /**
     * 给一些内容上色，加背景色
     * @param font
     * @param background
     * @param contents
     */
    public static void print(Color font, Color background, Object... contents) {
        print(ColorStyle.of(font, background), contents);
    }

    /**
     * 你自己玩
     * @param font
     * @param background
     * @param style
     * @param contents
     */
    public static void print(Color font, Color background, Style style, Object... contents) {
        print(ColorStyle.of(font, background, style), contents);
    }
    
    /**
     * DIY
     * @param style
     * @param contents
     */
    public static void print(ColorStyle style, Object... contents) {
        if(contents == null) {
            return;
        }
        for (Object content : contents) {
            print0(null, style, content);
        }
    }

    /**
     * 通用打印
     * 默认绿色，绿色是和平的颜色
     * @param contents
     */
    public static void printd(Object... contents) {
        print(PRINTD_STYLE, contents);
    }

    /**
     * 给一些内容上色，打印每个内容后都会换行
     * @param font
     * @param contents
     */
    public static void println(Color font, Object... contents) {
        println(ColorStyle.of(font), contents);
    }
    /**
     * 给一些内容上色，打印每个内容后都会换行
     * @param font
     * @param contents
     */
    public static void println(Color font, Style style, Object... contents) {
        println(ColorStyle.of(font, style), contents);
    }
    /**
     * 给一些内容上色，加背景色，打印每个内容后都会换行
     * @param font
     * @param contents
     */
    public static void println(Color font, Color background, Object... contents) {
        println(ColorStyle.of(font, background), contents);
    }

    /**
     * DIY，换行版
     * @param style
     * @param contents
     */
    public static void println(ColorStyle style, Object... contents) {
        for (Object content : contents) {
            println0(null, style, content);
        }
    }
    /**
     * 通用换行打印
     * 默认绿色，绿色是和平的颜色
     * @param contents
     */
    public static void printlnd(Object... contents) {
        println(PRINTLND_STYLE, contents);
    }

    /**
     * 换行，爱用不用
     */
    public static void println() {
        getGlobalPrintStream().println();
    }

    /**
     * 多次打印同一个对象
     * 我想打印 520次 “我爱你”，每次(相邻)的颜色还不一样。
     * @param source 需要重复打印的对象
     * @param times 次数
     * @param styles 样式
     */
    public static void printm(Object source, int times, ColorStyle... styles) {
        if(styles == null) {
            for (int i = 0; i < times; i++) {
                print((ColorStyle)null, Objects.toString(source));
            }
        } else {
            for (int i = 0; i < times; i++) {
                print(styles[i % styles.length], source);
            }
        }
    }

    /**
     * 多次打印同一个对象
     * 默认彩虹色
     * @param source
     * @param times
     */
    public static void printmd(Object source, int times) {
        printm(source, times, PRINTMD_STYLES);
    }

    /**
     * 对正则匹配的部分进行着色
     * @param style
     * @param regex
     * @param contents
     */
    public static void printr(ColorStyle style, String regex, Object... contents) {
        Pattern pattern = Pattern.compile(regex);
        for (int i = 0; i < contents.length; i++) {
            colorRegex(null, style, pattern, Objects.toString(contents[i]));
        }
    }

    public static void printr(Color color, String regex, Object... contents) {
        printr(ColorStyle.of(color), regex, contents);
    }

    public static void printr(Color color, Style style, String regex, Object... contents) {
        printr(ColorStyle.of(color, style), regex, contents);
    }

    /**
     * 对正则匹配的部分进行着色
     * 默认使用红色
     * @param regex
     * @param contents
     */
    public static void printrd(String regex, Object... contents) {
        printr(PRINTRD_STYLE, regex, contents);
    }

    /**
     * 对正则匹配部分进行着色，并且按照分组进行分别着色
     * 单个匹配项的每个分组使用不同的颜色，但多个匹配项的同一个分组使用相同的颜色
     * @param colorStyles
     * @param regex
     * @param contents
     */
    public static void printg(List<ColorStyle> colorStyles, String regex, Object... contents) {
        Pattern pattern = Pattern.compile(regex);
        for (int i = 0; i < contents.length; i++) {
            colorGroup(null, colorStyles, pattern, Objects.toString(contents[i]));
        }
    }

    /**
     * 对正则匹配部分进行着色，并且按照分组进行分别着色
     * 默认使用彩虹色
     * @param regex
     * @param contents
     */
    public static void printgd(String regex, Object... contents) {
        printg(PRINTGD_STYLES, regex, contents);
    }

    /**
     * 自动给对象上色，区分字母，数字，=号和:号
     * @param colorStyles
     * @param contents
     */
    public static void printa(List<ColorStyle> colorStyles, Object... contents) {
        // printg会忽略掉第一个颜色，这里补充一个无用的颜色
        colorStyles.add(0, ColorStyle.of(Color.DEFAULT));
        printg(colorStyles, AUTO_PRINT_REGEX, contents);
    }

    /**
     * 自动给对象上色，字母是红色，等号或冒号是蓝色，数字是绿色
     * 但凡用放大镜观察过电视机的人，都知道 RGB
     * @param contents
     */
    public static void printad(Object... contents) {
        printa(PRINTAD_STYLES,  contents);
    }

    /**
     * 用正则匹配字符串，然后给每一个小组一点颜色看看
     * @param colorStyles
     * @param pattern
     * @param source
     */
    private static void colorGroup(PrintStream printStream, List<ColorStyle> colorStyles, Pattern pattern, String source) {
        Matcher matcher = pattern.matcher(source);
        int index = 0;
        int start0;
        String group0, groupI;
        while(matcher.find()) {
            start0 = matcher.start();
            group0 = matcher.group();
            // 打印匹配前的内容
            if(index < start0) {
                printStream.print(source.substring(index, start0));
            }
            // 移动下标位置到匹配处
            index = start0;
            // 分组上色
            for(int i = 1; i <= matcher.groupCount(); i++) {
                groupI = matcher.group(i);
                if(groupI != null) {
                    // 最外面一组，用第一个颜色，其余的分组跟着下标变
                    if(matcher.start(i) > index) {
                        colorStr(printStream, colorStyles.get(0), source.substring(index, matcher.start(i)));
                    }
                    // 给该分组上色，按下标依次取色，排除最外层用的第一个样式
                    colorStr(printStream, colorStyles.get((i-1) % (colorStyles.size() - 1) + 1), groupI);
                    // 更新已完成扫描的下标
                    index = matcher.start(i) + groupI.length();
                }
            }
            // 如果没有匹配完，证明后面还有最外层的内容，用第一个样式上色
            if(index < start0 + group0.length()) {
                colorStr(printStream, colorStyles.get(0), source.substring(index, start0 + group0.length()));
            }
            // 更新已完成扫描的下标
            index = start0 + group0.length();
        }
        // 如果后面还有没有匹配正则的文本，则按照系统样式打印
        if(index < source.length()) {
            printStream.print(source.substring(index));
        }
    }

    /**
     * 用正则匹配字符串，给匹配到的字符串一点颜色看看
     * 注释参考 colorGroup，这个要简单得多
     * @param style
     * @param pattern
     * @param source
     */
    private static void colorRegex(PrintStream printStream, ColorStyle style, Pattern pattern, String source) {
        Matcher matcher = pattern.matcher(source);
        int index = 0;
        int start0;
        String group0;
        while(matcher.find()) {
            start0 = matcher.start();
            group0 = matcher.group();
            if(index < start0) {
                printStream.print(source.substring(index, start0));
            }
            colorStr( printStream, style, group0);
            index = start0 + group0.length();
        }
        if(index < source.length()) {
            printStream.print(source.substring(index));
        }
    }

    /**
     * 给字符串一点颜色看看
     * @param printStream   打印流
     * @param style         颜色样式
     * @param string        需要打印的字符串
     */
    private static void colorStr(PrintStream printStream, ColorStyle style, String string) {
        if (style == null) {
            if(printStream == null) {
                getGlobalPrintStream().print(string);
            } else {
                printStream.print(string);
            }
        } else {
            if (printStream == null) {
                getGlobalPrintStream().printf(PRINT_TEMPLATE, style.getBackground().getBgCode(), style.getFont().getCode(), style.getStyle().getCode(), string);
            } else {
                printStream.printf(PRINT_TEMPLATE, style.getBackground().getBgCode(), style.getFont().getCode(), style.getStyle().getCode(), string);
            }
        }
    }
    /**
     * 给对象一点颜色看看
     * 等等，我没有对象
     * @param style
     * @param obj
     */
    private static void print0(PrintStream printStream, ColorStyle style, Object obj) {
        colorStr(printStream, style, Objects.toString(obj));
    }

    /**
     * 给对象一点颜色看看，并换行
     * 三姨婆又给我介绍了个对象？它多大了，重写了 toString 没有。
     * @param printStream 如果是 null 就代表使用 GLOBAL_PRINT_STREAM
     * @param style
     * @param obj
     */
    private static void println0(PrintStream printStream, ColorStyle style, Object obj) {
        colorStr(printStream, style, Objects.toString(obj) + System.lineSeparator());
    }

    /*
     * 默认颜色，不喜欢可以自行修改
     */

    public static ColorStyle PRINTD_STYLE = ColorStyle.of(Color.GREEN, Style.BOLD);
    public static ColorStyle PRINTLND_STYLE = ColorStyle.of(Color.GREEN, Style.BOLD);
    public static ColorStyle PRINTRD_STYLE = ColorStyle.of(Color.RED);
    public static List<ColorStyle> PRINTGD_STYLES = ColorStyle.ofList(Color.rainbow());
    public static List<ColorStyle> PRINTAD_STYLES = ColorStyle.ofList(Color.RED, Color.GREEN, Color.BLUE);
    public static ColorStyle[] PRINTMD_STYLES = ColorStyle.ofArray(Color.rainbow());

    public static String AUTO_PRINT_REGEX = "([a-zA-Z]+)|(\\d+(?:\\.\\d+)?)|([=:])|([{}]).*?";


}
