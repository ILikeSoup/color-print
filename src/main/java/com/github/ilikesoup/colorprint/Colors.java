package main.java.com.github.ilikesoup.colorprint;

import java.util.Objects;

/**
 * 将对象包装成可输出样式的对象
 * 用于 pintf 中的传参，可以将指定的参数输出为带样式的参数
 * 我的核心方法是 toString, 哈哈，想不到吧。
 */
public final class Colors {
    /**
     * 到我怀里来，给他们点颜色看看
     * 禁止言语攻击，想说就写到 toString 里
     */
    private final Object obj;
    /**
     * 你想让他们看什么颜色？
     */
    private final ColorStyle cs;

    /**
     * 看不见我，看不见我
     * @param obj
     * @param cs
     */
    private Colors(Object obj, ColorStyle cs) {
        this.obj = obj;
        this.cs = cs;
    }

    /**
     * 颜色？我拿不定主意。
     * 那，那就红色吧！
     * @param obj
     * @return
     */
    public static Colors wrap(Object obj) {
        return new Colors(obj, ColorStyle.of(Color.RED));
    }

    /**
     * 什么颜色，你自己选。
     * 下面几个都一样，反正我只负责给他们点颜色看看。
     * @param obj
     * @param cs
     * @return
     */
    public static Colors wrap(Object obj, ColorStyle cs) {
        return new Colors(obj, cs);
    }

    public static Colors wrap(Object obj, Color color) {
        return new Colors(obj, ColorStyle.of(color));
    }

    public static Colors wrap(Object obj, Color color, Style style) {
        return new Colors(obj, ColorStyle.of(color, style));
    }

    public static Colors wrap(Object obj, Color color, Color bgColor) {
        return new Colors(obj, ColorStyle.of(color, bgColor));
    }

    public static Colors wrap(Object obj, Color color, Color bgColor, Style style) {
        return new Colors(obj, ColorStyle.of(color, bgColor, style));
    }

    /**
     * 等了好多年，终于成为了核心方法
     * @return
     */
    @Override
    public String toString() {
        return String.format(ColorPrint.PRINT_TEMPLATE, cs.getBackground().getBgCode(), cs.getFont().getCode(), Objects.toString(obj));
    }
}
