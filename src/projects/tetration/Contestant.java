package projects.tetration;

import osbjava.objects.OsbColor;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


@SuppressWarnings("unused")
public enum Contestant {

    BARF_BAG,
    BASKETBALL,
    BELL,
    BLACK_HOLE,
    BOMBY,
    BOOK,
    BOTTLE,
    CAKE,
    CLOCK,
    CLOUDY,
    COINY,
    DONUT,
    EGGY,
    ERASER,
    FANNY,
    FOLDY,
    FRIES,
    GATY,
    GOLF_BALL,
    GRASSY,
    ICE_CUBE,
    LIGHTNING,
    LIY,
    MARKER,
    NAILY,
    NEEDLE,
    NICKEL,
    PEN,
    PENCIL,
    PIE,
    PILLOW,
    PIN,
    PRICE_TAG,
    PUFFBALL,
    REMOTE,
    ROBOT_FLOWER,
    ROCKY,
    SAW,
    SNOWBALL,
    TEARDROP,
    TENNIS_BALL,
    TREE,
    TV,
    WINNER,
    YELLOW_FACE;


    private static final Random RANDOM = new Random(System.currentTimeMillis());


    public String fileName() {
        return switch (this) {
            case BARF_BAG ->        "barfbag.png";
            case BASKETBALL ->      "basketball.png";
            case BELL ->            "bell.png";
            case BLACK_HOLE ->      "blackhole.png";
            case BOMBY ->           "bomby.png";
            case BOOK ->            "book.png";
            case BOTTLE ->          "bottle.png";
            case CAKE ->            "cake.png";
            case CLOCK ->           "clock.png";
            case CLOUDY ->          "cloudy.png";
            case COINY ->           "coiny.png";
            case DONUT ->           "donut.png";
            case EGGY ->            "eggy.png";
            case ERASER ->          "eraser.png";
            case FANNY ->           "fanny.png";
            case FOLDY ->           "foldy.png";
            case FRIES ->           "fries.png";
            case GATY ->            "gaty.png";
            case GOLF_BALL ->       "golfball.png";
            case GRASSY ->          "grassy.png";
            case ICE_CUBE ->        "icecube.png";
            case LIGHTNING ->       "lightning.png";
            case LIY ->             "liy.png";
            case MARKER ->          "marker.png";
            case NAILY ->           "naily.png";
            case NEEDLE ->          "needle.png";
            case NICKEL ->          "nickel.png";
            case PEN ->             "pen.png";
            case PENCIL ->          "pencil.png";
            case PIE ->             "pie.png";
            case PILLOW ->          "pillow.png";
            case PIN ->             "pin.png";
            case PRICE_TAG ->       "pricetag.png";
            case PUFFBALL ->        "puffball.png";
            case REMOTE ->          "remote.png";
            case ROBOT_FLOWER ->    "robotflower.png";
            case ROCKY ->           "rocky.png";
            case SAW ->             "saw.png";
            case SNOWBALL ->        "snowball.png";
            case TEARDROP ->        "teardrop.png";
            case TENNIS_BALL ->     "tennisball.png";
            case TREE ->            "tree.png";
            case TV ->              "tv.png";
            case WINNER ->          "winner.png";
            case YELLOW_FACE ->     "yellowface.png";
        };
    }


    public int fileWidth() {

        String path = "./src/projects/tetration/bodies/";
        BufferedImage img;
        int width;

        try {
            img = ImageIO.read(new File(path + this.fileName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        width = img.getWidth();
        return width;
    }


    public int fileHeight() {

        String path = "./src/projects/tetration/bodies/";
        BufferedImage img;
        int height;

        try {
            img = ImageIO.read(new File(path + this.fileName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        height = img.getHeight();
        return height;
    }


    public OsbColor color() {
        return switch (this) {
            case BARF_BAG ->        new OsbColor("E1C1AA");
            case BASKETBALL ->      new OsbColor("F69368");
            case BELL ->            new OsbColor("D99A75");
            case BLACK_HOLE ->      new OsbColor("000000");
            case BOMBY ->           new OsbColor("626262");
            case BOOK ->            new OsbColor("139CB9");
            case BOTTLE ->          new OsbColor("E8FFF8");
            case CAKE ->            new OsbColor("8D716D");
            case CLOCK ->           new OsbColor("5F7AAD");
            case CLOUDY ->          new OsbColor("FFFFFF");
            case COINY ->           new OsbColor("EC9F00");
            case DONUT ->           new OsbColor("F5E0B0");
            case EGGY ->            new OsbColor("FEF9F1"); //
            case ERASER ->          new OsbColor("E47E93");
            case FANNY ->           new OsbColor("285AD0");
            case FOLDY ->           new OsbColor("84D4C2");
            case FRIES ->           new OsbColor("DC3632");
            case GATY ->            new OsbColor("EEECFF"); //
            case GOLF_BALL ->       new OsbColor("F7F7F7"); //
            case GRASSY ->          new OsbColor("62C41F");
            case ICE_CUBE ->        new OsbColor("EBEFFF"); //
            case LIGHTNING ->       new OsbColor("FDF379");
            case LIY ->             new OsbColor("BBCFF1");
            case MARKER ->
                { // Marker the Green
                    if (RANDOM.nextInt() % 2 == 0)
                        yield new OsbColor("9252FC");
                    else
                        yield new OsbColor("89DA74");
                }
            case NAILY ->           new OsbColor("CDCDCD");
            case NEEDLE ->          new OsbColor("CCCCCC");
            case NICKEL ->          new OsbColor("9FA4A2");
            case PEN ->             new OsbColor("469FFF");
            case PENCIL ->          new OsbColor("FFAA0A");
            case PIE ->             new OsbColor("E9C798");
            case PILLOW ->          new OsbColor("FDFAFE"); //
            case PIN ->             new OsbColor("F00F0F");
            case PRICE_TAG ->       new OsbColor("F93A3A");
            case PUFFBALL ->        new OsbColor("FFCCFF");
            case REMOTE ->          new OsbColor("333333");
            case ROBOT_FLOWER ->    new OsbColor("D66BCC");
            case ROCKY ->           new OsbColor("999999");
            case SAW ->             new OsbColor("FF91A4"); //
            case SNOWBALL ->        new OsbColor("F4F4F4"); //
            case TEARDROP ->        new OsbColor("42C7FC");
            case TENNIS_BALL ->     new OsbColor("E6F905"); //
            case TREE ->
                { // Tree the Purple
                if (RANDOM.nextInt() % 2 == 0)
                    yield new OsbColor("00C444");
                else
                    yield new OsbColor("AA5CCA");
                }
            case TV ->              new OsbColor("BBBBBB");
            case WINNER ->          new OsbColor("ABB3FF");
            case YELLOW_FACE ->     new OsbColor("FFFF00");
        };
    }


    public static Contestant toEnum(int ordinal) {
        return Contestant.values()[ordinal];
    }


    public static int length() {
        return Contestant.values().length;
    }


}
