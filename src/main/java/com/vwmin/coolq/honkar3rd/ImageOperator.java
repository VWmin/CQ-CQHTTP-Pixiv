package com.vwmin.coolq.honkar3rd;

import com.vwmin.coolq.honkar3rd.entities.*;
import com.vwmin.coolq.honkar3rd.entities.Character;
import com.vwmin.coolq.honkar3rd.gacha.GachaType;
import com.vwmin.coolq.honkar3rd.gacha.RandomUtil;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/22 18:44
 */
public class ImageOperator {
    private final ResourceManager resourceManager;
    private final BufferedImage image;
    private static Map<String, BufferedImage> symbols = null; //数字0-9及斜杠

    public ImageOperator(GachaType type, List<Item> items, ResourceManager resourceManager)throws IOException {
        this.resourceManager = resourceManager;
        image = ImageIO.read(resourceManager.loadBackgroundImage().getInputStream());
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_NORMALIZE);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        if (symbols == null){
            symbols = initSymbols();
        }

        items = preProcess(items);

        drawRandomNumber();
        drawGachaType(type);
        drawItems(items);
    }

    private List<Item> preProcess(List<Item> items) {

        /*合并*/
        Map<String, Item> map = new HashMap<>();
        for (Item item : items){
            String key = item.getRealName();
            if (!map.containsKey(key)){
                map.put(key, item);
            }else {
                Item exist = map.get(key);
                //抽到重复角色卡
//                if (exist instanceof Character && !((Character) exist).isDebris()){
//                    if (map.containsKey(item.getRealName()+"碎片")){
//                        //先前抽到了该角色碎片
//                        Character someDebris = (Character) map.get(item.getRealName() + "碎片");
//                        someDebris.setAmount(someDebris.getAmount() + item.getAmount());
//                    }else {
//                        //将抽到的角色卡转换成碎片
//                        item.setRealName(item.getRealName() + "碎片");
//                        ((Character) item).setDebris(true);
//                        map.put(item.getRealName(), item);
//                    }
//                }
                if (exist instanceof Material){
                    exist.setAmount(exist.getAmount() + item.getAmount());
                }
            }
        }

        /*排序*/
        items = new ArrayList<>(map.values());
        items.sort(new ItemComparator());

        return items;
    }

    private Map<String, BufferedImage> initSymbols() throws IOException {
        Map<String, BufferedImage> map = new HashMap<>();
        for (int i=0; i<10; i++){
            map.put(i+"", ImageIO.read(resourceManager.loadNumberImage(i).getInputStream()));
        }
        map.put("斜杠", ImageIO.read(resourceManager.loadSlashImage().getInputStream()));
        return map;
    }

    private void drawRandomNumber() {
        /*设定随机数值区间*/
        //体
        final int jiecao = 160;
        //金币
        final int gold = 4000000;
        //水
        final int crystal = 28000;

        int randJiecao = RandomUtil.nextInt(0, jiecao+1);
        int randGold = RandomUtil.nextInt(0, gold);
        int randCrystal = RandomUtil.nextInt(0, crystal);

        drawNumber(randJiecao, jiecao, 1364);
        drawNumber(randGold, null, 1700);
        drawNumber(randCrystal, null, 2028);
    }

    /**
     * 以指定的startCenter为中心，向两边延伸绘制数字，指定了上限时，将会在中心绘制斜杠
     * 函数是不会判断越界的
     * @param num 绘制值
     * @param top 存在时将会绘制上限，不会判断与绘制值的大小关系哦
     * @param startCenter 起始中心
     */
    private void drawNumber(Integer num, Integer top, Integer startCenter) {
        Deque<String> symbolsQueue = new ArrayDeque<>();
        if (top != null){
            while (top != 0){
                symbolsQueue.addLast(top%10+"");
                top /= 10;
            }
            symbolsQueue.addLast("斜杠");
        }
        while (num != 0){
            symbolsQueue.addLast(num%10 +"");
            num /= 10;
        }

        int length = symbolsQueue.size();
        int w = 18; //影响密度

        // 从右边往左画
        int startPos = startCenter + w*length/2 - w;
        for (int i=0; i<length; i++){
            image.getGraphics().drawImage(symbols.get(symbolsQueue.removeFirst()), startPos-i*w, 44,
                    21, 29, null);
        }


    }

    private void drawGachaType(GachaType type) throws IOException {
        BufferedImage toDraw = ImageIO.read(resourceManager.loadGachaTypeImage(type).getInputStream());

        image.getGraphics().drawImage(toDraw, 10, image.getHeight()-toDraw.getHeight()-10,
                toDraw.getWidth(), toDraw.getHeight(), null);
    }

    private void drawItems(List<Item> items) throws IOException {
        for (int i=0; i<items.size(); i++){
            if (i >= 14) break; //画框只能容下这么点
            Item item = items.get(i);
            BufferedImage itemImage = getDecoratedImage(item);
            BufferedImage icon = getIcon(item);
            drawItem(itemImage, icon, i);
        }
    }

    private BufferedImage getIcon(Item item) throws IOException {
        BufferedImage icon = null;
        if (item instanceof Stigma){
            icon = ImageIO.read(resourceManager.loadStigmaIcon(((Stigma) item).getPosition()).getInputStream());
        }else if (item instanceof Character && ((Character) item).isDebris()){
            // FIXME: 2020/4/24 缺少碎片图标素材
        }
        return icon;
    }

    private BufferedImage getDecoratedImage(Item item) throws IOException {
        BufferedImage background = ImageIO.read(resourceManager.loadItemFrameImage(item.getRarity()).getInputStream());
        Graphics2D graphics = background.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_NORMALIZE);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        if (item instanceof Character){
            BufferedImage itemImage = ImageIO.read(
                    resourceManager.loadCharacterAvatar(item.getRealName(), ((Character) item).isDebris())
                    .getInputStream()
            );

            // 绘制角色or碎片图标
            background.getGraphics().drawImage(itemImage, 5, 14,
                    196, 172, null); // todo 参数抄的，慢慢改

            if (!((Character) item).isDebris()){
                //绘制rank图标
                drawValkyrieRank(background, ((Character) item).getRank());
            }else {
                //绘制数量
                drawAmount(background, item.getAmount(), "×");
                //绘制左上小碎片图标
            }

        }
        else if (item instanceof Stigma){
            BufferedImage itemImage = ImageIO.read(resourceManager.loadStigmaImage(item.getRealName()).getInputStream());
            background.getGraphics().drawImage(itemImage, 5, 14,
                    196, 172, null);
            // 绘制圣痕星级图标
            drawMaterialRarity(background, item.getRarity(), ((Equipment) item).getMaxRarity());

            // 绘制等级
            drawAmount(background, ((Equipment) item).getLv(), "lv.");
        }
        else if (item instanceof Equipment){
            BufferedImage itemImage = ImageIO.read(resourceManager.loadWeaponImage(item.getRealName()).getInputStream());
            background.getGraphics().drawImage(itemImage, 5, 14,
                    196, 172, null);
            // 绘制装备星级图标
            drawMaterialRarity(background, item.getRarity(), ((Equipment) item).getMaxRarity());

            // 绘制等级
            drawAmount(background, ((Equipment) item).getLv(), "lv.");
        }
        else if (item instanceof Material ){
            BufferedImage itemImage = ImageIO.read(resourceManager.loadMaterialImage(item.getRealName()).getInputStream());

            // 绘制材料图标
            background.getGraphics().drawImage(itemImage, 5, 14,
                    196, 172, null);

            // 绘制材料星级图标
            drawMaterialRarity(background, item.getRarity(), item.getRarity());

            // 绘制数量
            drawAmount(background, item.getAmount(), "×");
        }

        return background;
    }

    private void drawAmount(BufferedImage background, int amount, String prefix) {
        String content = prefix+amount;
        Font font = resourceManager.loadFont();
        int w = FontDesignMetrics.getMetrics(font).charsWidth(content.toCharArray(), 0, content.length());
        Graphics graphics = background.getGraphics();
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        graphics.drawString(content, 105-w/2, 235);
    }

    private void drawMaterialRarity(BufferedImage background, int rarity, int maxRarity) throws IOException {
        if (rarity >5 || rarity<1 || maxRarity>5 || maxRarity<1){
            throw new IllegalArgumentException("unexpected material rarity, got: "+ rarity +", and: "+ maxRarity);
        }
        BufferedImage star = ImageIO.read(resourceManager.loadStar(false).getInputStream());
        BufferedImage grayStar = ImageIO.read(resourceManager.loadStar(true).getInputStream());

        final int wl = background.getWidth();
        final int ws = 28;

        int startPos = (wl - ws*maxRarity) / 2;
        for (int i=0; i<maxRarity; i++){
            if (i < rarity){
                background.getGraphics().drawImage(star, startPos+i*ws, 171,
                        33, 33, null  );
            }else {
                background.getGraphics().drawImage(grayStar, startPos+i*ws, 171,
                        33, 33, null );
            }

        }


    }

    private void drawValkyrieRank(BufferedImage background, String rank) throws IOException {
        BufferedImage rankImage = ImageIO.read(resourceManager.loadValkyrieRankImage(rank).getInputStream());
        background.getGraphics().drawImage(rankImage, 53, 160,
                106, 91, null);
    }

    private void drawItem(BufferedImage item, BufferedImage icon, int i) throws IOException {
        final int xPos = 160 + 300 * (i%7);
        final int yPos = 190 + (i > 7-1 ? 340 : 0);
        this.image.getGraphics().drawImage(item, xPos, yPos, item.getWidth(), item.getHeight(), null);

        // 绘制左上小图标
        if (icon != null){
            final int w = 55, h = 55;
            this.image.getGraphics().drawImage(icon, xPos-w/3, yPos-h/3,
                    w, h, null);
        }
    }

    public String draw(String path) throws IOException {
        String file = System.currentTimeMillis()+".png";
        ImageIO.write(image, "png", new File(path+file));
        return file;
    }

}
