package com.github.ilikesoup.colorprint;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 颜色样式
 * 如果你把字体颜色和背景色设置成了一样的，那么我想说，你真是个人才！
 * 一个无聊的类
 */
public class ColorStyle {
    // 字体颜色
    private final Color font;
    // 背景色
    private final Color background;
    // 样式
    private final Style style;

    private ColorStyle(Color font, Color background) {
        this(font, background, Style.SKIP);
    }

    // You are not a null... I will be with you...
    private ColorStyle(Color font, Color background, Style style) {
        this.font = font == null ? Color.DEFAULT : font;
        this.background = background == null ? Color.DEFAULT : background;
        this.style = style == null ? Style.SKIP : style;

    }

    public static ColorStyle of(Color font, Color background) {
        return new ColorStyle(font, background);
    }

    public static ColorStyle of(Color font, Color background, Style style) {
        return new ColorStyle(font, background, style);
    }

    public static ColorStyle of(Color font) {
        return new ColorStyle(font, Color.DEFAULT);
    }

    public static ColorStyle of(Color font, Style style) {
        return new ColorStyle(font, Color.DEFAULT, style);
    }

    public static ColorStyle bg(Color background) {
        return new ColorStyle(Color.DEFAULT, background);
    }

    public static ColorStyle bg(Color background, Style style) {
        return new ColorStyle(Color.DEFAULT, background, style);
    }

    public static List<ColorStyle> ofList(Color... fonts) {
        return Stream.of(fonts).map(ColorStyle::of).collect(Collectors.toList());
    }

    public static List<ColorStyle> ofList(Style style, Color... fonts) {
        return Stream.of(fonts).map(one -> of(one, style)).collect(Collectors.toList());
    }

    public static ColorStyle[] ofArray(Color... fonts) {
        return Stream.of(fonts).map(ColorStyle::of).toArray(ColorStyle[]::new);
    }

    public static ColorStyle[] ofArray(Style style, Color... fonts) {
        return Stream.of(fonts).map(one -> of(one, style)).toArray(ColorStyle[]::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorStyle colorStyle = (ColorStyle) o;
        return font == colorStyle.font &&
                background == colorStyle.background;
    }

    @Override
    public int hashCode() {
        return Objects.hash(font, background);
    }

    public Color getFont() {
        return font;
    }

    public Color getBackground() {
        return background;
    }

    public Style getStyle() {
        return style;
    }

    @Override
    public String toString() {
        return "ColorPair{" +
                "font=" + font +
                ", background=" + background +
                '}';
    }
}
