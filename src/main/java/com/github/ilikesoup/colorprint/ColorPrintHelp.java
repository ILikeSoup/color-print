package com.github.ilikesoup.colorprint;

/**
 * ColorPrint使用帮助类
 */
public final class ColorPrintHelp {

    public static void list() {
        description();  // 组件描述
        functions();    // 功能描述
        useCase();      // 使用案例
        findColors();   // 颜色大全
    }

    public static void description() {
        ColorPrint.of(ColorStyle.of(Color.L_BLUE, Style.BOLD))
                .println("ColorPrint")
                .print("  彩色打印：用于在调试时改变打印文字的颜色和样式，突出重要部分。").println();
        ColorPrint.println();
    }

    public static void functions() {
        ColorPrint.RAINBOW_BOLD.println(
                "功能清单",
                "  print/println: 带颜色的通用打印",
                "  printr[regex]: 突出正则匹配部分的打印",
                "  printg[group]: 突出正则匹配部分的打印，并且对分组进行颜色处理",
                "  printa[auto]: 自动对字母数字等字符进行处理",
                "  printm[multi]: 对同一对象进行多次打印",
                "  print?d[default]: 无需指定颜色，采用默认颜色，支持上述所有功能"
        );
        ColorPrint.println();
        ColorPrint.println();
    }

    public static void useCase() {
        printCase();
        printlnCase();
        printrCase();
        printgCase();
        printaCase();
        printmCase();
    }

    private static final ColorStyle title = ColorStyle.of(Color.CYAN, Color.BLUE, Style.REVERSAL);
    private static final ColorStyle subTitle = ColorStyle.of(Color.L_GRAY, Color.GRAY);
    private static final ColorStyle style = ColorStyle.of(Color.L_BLUE, Style.REVERSAL);

    public static void printCase() {
        ColorPrint.println(title, "print 使用案列 | 通用打印");
        ColorPrint.println(subTitle, "1 直接使用静态方法");
        ColorPrint.println(style, "ColorPrint.print(Color.BLUE, \"去吧！\", \"皮卡丘！\");");
        ColorPrint.print(Color.BLUE, "去吧！", "皮卡丘！");
        ColorPrint.println();
        ColorPrint.println(subTitle, "2 构建对象使用成员方法，链式调用");
        ColorPrint.println(style, "ColorPrint.of(Color.PINK, Color.L_RED, Color.L_PINK).print(\"A\", \"B\", \"C\", \"D\", \"E\", \"F\", \"G\")");
        ColorPrint.println(style, "          .print(\"W\", \"L\", \"S\", \"B\");");
        ColorPrint.of(Color.PINK, Color.L_RED, Color.L_PINK).print("A", "B", "C", "D", "E", "F", "G")
            .print("W", "L", "S", "B");
        ColorPrint.println();
        ColorPrint.println();
    }

    public static void printlnCase() {
        ColorPrint.println(title, "println 使用案列 | 通用换行打印");
        ColorPrint.println(subTitle, "和print不同的是，println在打印传入的每一个对象之后都会换行。");
        ColorPrint.println(subTitle, "1 直接使用静态方法");
        ColorPrint.println(style, "ColorPrint.println(Color.BLUE, \"去吧！\", \"皮卡丘！\");");
        ColorPrint.println(Color.BLUE, "去吧！", "皮卡丘！");
        ColorPrint.println(subTitle, "2 构建对象使用成员方法，链式调用");
        ColorPrint.println(style, "ColorPrint.of(Color.PINK, Color.L_RED, Color.L_PINK).println(\"A\", \"B\", \"C\", \"D\", \"E\", \"F\", \"G\")");
        ColorPrint.println(style, "          .println(\"W\", \"L\", \"S\", \"B\");");
        ColorPrint.of(Color.PINK, Color.L_RED, Color.L_PINK).println("A", "B", "C", "D", "E", "F", "G")
                .println("W", "L", "S", "B");
        ColorPrint.println();
    }

    public static void printrCase() {
        ColorPrint.println(title, "printr 使用案列 | 正则匹配突出打印");
        ColorPrint.println(subTitle, "1 直接使用静态方法");
        ColorPrint.println(style, "ColorPrint.printr(Color.GREEN, Style.REVERSAL, \"\\\\d\",\"苹果原价5元一斤，现在做活动打5折，问：现在苹果多少钱一斤？\");");
        ColorPrint.printr(Color.GREEN, Style.REVERSAL, "\\d","苹果原价5元一斤，现在做活动打5折，问：现在苹果多少钱一斤？");
        ColorPrint.println();
        ColorPrint.println(style, "ColorPrint.printr(Color.RED,\"\\\\$\\\\{(\\\\w+(?:\\\\.\\\\w+))(:\\\\w+)?\\\\}\", \"姓名：${user.name:string}，年龄：${user.age:int}\");");
        ColorPrint.printr(Color.RED,"\\$\\{(\\w+(?:\\.\\w+))(:\\w+)?\\}", "姓名：${user.name:string}，年龄：${user.age:int}");
        ColorPrint.println();
        ColorPrint.println(subTitle, "2 构建对象使用成员方法，链式调用，可以使用多个颜色");
        ColorPrint.println(style, "ColorPrint.RAINBOW.printr(\"\\\\d\", \"《编号89757》\", \"《1000年以后》\", \"《1874》\").println();");
        ColorPrint.RAINBOW.printr("\\d", "《编号89757》", "《1000年以后》", "《1874》").println();
        ColorPrint.println();
    }

    public static void printgCase() {
        ColorPrint.println(title, "printg 使用案列 | 正则匹配突出分组增强打印");
        ColorPrint.println(subTitle, "1 直接使用静态方法");
        ColorPrint.println(subTitle, "由于颜色要和最外面的分组区分开，所以至少需要指定两个颜色，最好尽可能的接近分组数量。");
        ColorPrint.println(style, "ColorPrint.printg(ColorStyle.ofList(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW), \"\\\\$\\\\{(\\\\w+(?:\\\\.\\\\w+))(:\\\\w+)?\\\\}\",\"姓名：${user.name:string}，年龄：${user.age:int}\");");
        ColorPrint.printg(ColorStyle.ofList(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW), "\\$\\{(\\w+(?:\\.\\w+))(:\\w+)?\\}","姓名：${user.name:string}，年龄：${user.age:int}");
        ColorPrint.println();
        ColorPrint.println(subTitle, "2 构建对象使用成员方法，链式调用");
        ColorPrint.println(style, "ColorPrint.RAINBOW.printr(\"\\\\$\\\\{(\\\\w+(?:\\\\.\\\\w+))(:\\\\w+)?\\\\}\",\"姓名：${user.name:string}，年龄：${user.age:int}\").println();");
        ColorPrint.RAINBOW.printg("\\$\\{(\\w+(?:\\.\\w+))(:\\w+)?\\}", "姓名：${user.name:string}，年龄：${user.age:int}").println();
        ColorPrint.println();
    }

    public static void printaCase() {
        ColorPrint.println(title, "printa 使用案列 | 自动对字母数字等进行颜色处理");
        ColorPrint.println(subTitle, "内部使用的printg，能满足一些使用场景，当不满足使用场景时使用printg");
        ColorPrint.println(subTitle, "1 直接使用静态方法");
        ColorPrint.printa(ColorStyle.ofList(Color.RED, Color.GREEN, Color.BLUE), "个人信息：name=Paul, age=22");
        ColorPrint.println();
        ColorPrint.println(subTitle, "2 构建对象使用成员方法，链式调用。");
        ColorPrint.println(style, "ColorPrint.of(Color.rainbow()).printa(\"{name:paul, age:22}\").println();");
        ColorPrint.of(Color.rainbow()).printa("{name:paul, age:22}").println();
        ColorPrint.println();
    }

    public static void printmCase() {
        ColorPrint.println(title, "printm 使用案列 | 重复打印");
        ColorPrint.println(subTitle, "1 直接使用静态方法");
        ColorPrint.println(style, "ColorPrint.printm(\" 我爱你 \", 10, ColorStyle.ofArray(Style.REVERSAL, Color.RED, Color.GREEN, Color.BLUE));");
        ColorPrint.printm(" 我爱你 ", 52, ColorStyle.ofArray(Style.REVERSAL, Color.RED, Color.GREEN, Color.BLUE));
        ColorPrint.println();
        ColorPrint.println(subTitle, "2 构建对象使用成员方法，链式调用。这个例子有没有勾起你的儿时记忆。");
        ColorPrint.println(style, "ColorPrint.of(Style.REVERSAL, Color.rainbow()).printm(\"  \", 50);");
        ColorPrint.of(Style.REVERSAL, Color.rainbow()).printm("  ", 50).println();
        ColorPrint.println();
    }

    public static void tips() {
        ColorPrint.println(title, "Tips ");
        ColorPrint.println(subTitle, "1 使用print?d方法，与对应的print? 方法类似，只是不需要指定颜色样式");
        ColorPrint.println(subTitle, "使用print?d方法，会使用默认的颜色样式，这些样式是类的公有静态变量，如果不喜欢可以进行修改");
        ColorPrint.println(subTitle, "2 如果使用默认的ColorPrint，应该只在代码调试阶段使用");
        ColorPrint.println(subTitle, "由于它终究是直接调用System.out进行同步输出，会影响实际的运行性能");
        ColorPrint.println(subTitle, "3 输出太多了，建议静态引入ColorPrint");
        ColorPrint.println(subTitle, "import static cn.soup.colorprint.ColorPrint.*");
        ColorPrint.println(subTitle, "4 使用ColorPrintHelp.findColors来寻找心仪的颜色搭配");
        ColorPrint.println();
    }

    public static void findColors() {
        ColorPrint.println(title, "颜色大全，尽情挑选喜欢的颜色组合吧。");
        ColorPrint.println(subTitle, "第一个数字表示字体颜色code，第二个数字表示背景颜色code，");
        ColorPrint.println(subTitle, "标记B的表示使用了Style.BOLD, 标记R的表示使用了Style.REVERSAL");
        ColorPrint.println();
        Color[] colors = Color.values();
        int k = 0;
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors.length; j++) {
                if(i == j) continue;
                ColorPrint.print(ColorStyle.of(colors[i], colors[j], Style.BOLD), colors[i].getCode(), ",", colors[j].getCode(), "|B");
                ColorPrint.print(ColorStyle.of(colors[i], colors[j], Style.REVERSAL), colors[i].getCode(), ",", colors[j].getCode(), "|R");
                if(++k % 8 == 0) {
                    ColorPrint.println();
                }
            }
        }
    }

}
