package com.vwmin.coolq.honkar3rd;

import com.vwmin.coolq.honkar3rd.gacha.GachaType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/24 16:44
 */
@Component
@Getter
public class ResourceManager {

    private final
    ResourceLoader resourceLoader;

    public ResourceManager(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /*概率设置*/
    @Value("file:resources/概率/底料概率.ini")
    private Resource baseSupplySetting;

    @Value("file:resources/概率/扩充概率A.ini")
    private Resource expansionSupplyASetting;

    @Value("file:resources/概率/扩充概率B.ini")
    private Resource expansionSupplyBSetting;

    @Value("file:resources/概率/精准概率.ini")
    private Resource focusedSupplySetting;

    /*补给设置*/
    @Value("file:resources/池子内容/base.json")
    private Resource baseSupplyContent;

    @Value("file:resources/池子内容/expansionA.json")
    private Resource expansionSupplyAContent;

    @Value("file:resources/池子内容/expansionB.json")
    private Resource expansionSupplyBContent;

    @Value("file:resources/池子内容/focusedA.json")
    private Resource focusedSupplyAContent;

    @Value("file:resources/池子内容/focusedB.json")
    private Resource focusedSupplyBContent;

    @Value("file:resources/池子内容/items.json")
    private Resource items;

    public Resource loadBackgroundImage(){
        return resourceLoader.getResource("file:resources/装备卡/框/抽卡背景.png");
    }

    public Resource loadNumberImage(int i){
        return resourceLoader.getResource("file:resources/数字/"+ i +".png");
    }
    
    public Resource loadSlashImage(){
        return resourceLoader.getResource("file:resources/数字/斜杠.png");
    }

    public Resource loadGachaTypeImage(GachaType type) {
        switch (type){
//            case EXPANSION_A_10:
//                return resourceLoader.getResource("file:resources/装备卡/框/扩充十连.png");
//            case EXPANSION_1:
//                return resourceLoader.getResource("file:resources/装备卡/框/扩充单抽.png");
//            case FOCUSED_A_1:
//                return resourceLoader.getResource("file:resources/装备卡/框/精准A单抽.png");
//            case FOCUSED_B_1:
//                return resourceLoader.getResource("file:resources/装备卡/框/精准B单抽.png");
            case FOCUSED_A_10:
                return resourceLoader.getResource("file:resources/装备卡/框/精准A十连.png");
            case FOCUSED_B_10:
                return resourceLoader.getResource("file:resources/装备卡/框/精准B十连.png");
            default:
                return resourceLoader.getResource("file:resources/装备卡/框/扩充十连.png");
        }
    }

    public Resource loadStigmaIcon(int pos){
        return resourceLoader.getResource("file:resources/装备卡/框/Stigmata"+ pos +".png");
    }

    public Resource loadItemFrameImage(int rarity){
        return resourceLoader.getResource("file:resources/装备卡/框/框"+ (rarity>3?"":"蓝") +".png");
    }

    public Resource loadCharacterAvatar(String name, boolean isDebris){
        return resourceLoader.getResource("file:resources/装备卡/"+ (isDebris?"碎片":"角色卡") +"/"+ name +".png");
    }

    public Resource loadStigmaImage(String name) {
        return resourceLoader.getResource("file:resources/装备卡/圣痕卡/"+ name +".png");
        
    }

    public Resource loadWeaponImage(String name) {
        return resourceLoader.getResource("file:resources/装备卡/武器/"+ name +".png");

    }

    public Resource loadMaterialImage(String name) {
        return resourceLoader.getResource("file:resources/装备卡/材料/"+ name +".png");
    }
    
    public Resource loadStar(boolean isGray) {
        return resourceLoader.getResource("file:resources/装备卡/框/" + 
                (isGray
                ? "StarBigGray.png"
                : "StarBig.png")
        );
    }

    public Resource loadValkyrieRankImage(String rank) {
        if ("S".equalsIgnoreCase(rank)){
            return resourceLoader.getResource("file:resources/装备卡/框/Star_Avatar_3M.png");
        }else if ("A".equalsIgnoreCase(rank)){
            return resourceLoader.getResource("file:resources/装备卡/框/Star_Avatar_2M.png");
        }else if ("B".equalsIgnoreCase(rank)){
            return resourceLoader.getResource("file:resources/装备卡/框/Star_Avatar_1M.png");
        }else {
            throw new IllegalArgumentException("unexpected valkyrie rank, got: " + rank);
        }
    }

    private Font font = null;
    public Font loadFont() {
        if (font != null) {
            return font;
        }
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    resourceLoader.getResource("file:resources/Arsenal-Italic-webfont.ttf").getFile());
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            font = Font.getFont(Font.SERIF);
        }
        this.font = font.deriveFont(Font.BOLD|Font.ITALIC, 35);
        return this.font;
    }
}
