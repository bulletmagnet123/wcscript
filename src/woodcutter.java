import org.osbot.rs07.accessor.XCharacter;
import org.osbot.rs07.api.*;
import org.osbot.rs07.api.Camera;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.input.mouse.RectangleDestination;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ScriptManifest(info = "FirstScript", logo = "", version = 1.0, author = "Bulletmagnet", name = "WoodCutter")
public class woodcutter extends Script {

    //------------User Settings---------------
    //You can also add this block of code at the end of the script between spoiler 5 and 6
    //------------Essentials---------------
    //Font size & Text Padding will determine the size of paint
    //Look at forum for examples
    final int baseFontSize = 14; //Recommended 12-20
    final int textPadding = 5; //space between text,columns, & rows
    final String title = "DylanSRT Paint Demo"; //add script name here
    final boolean warningEnabled = true; //Warning window pops up on paint if a player says one of the keywords, Make sure public chat is visible
    final String[] warningChatKeywords = {"bot", "botting", "report", "reported", "Botting", "Bot", "Report", "Reported"};

    //--Custom Counters - Read Forum for details
    //Set customInfo=null if you don't want to add anything



    PaintAPI paint;

    BufferedImage background;

    Area teak = new Area(2332, 3051, 2337, 3046);
    Area oak = new Area(3033, 3275, 3060, 3259);
    Area willow = new Area(3079, 3239, 3092, 3224);
    Area yew = new Area(2752, 3434, 2763, 3425);
    Area magic = new Area(2699, 3399, 2706, 3396);
    private Area Area = new Area(2752, 3434, 2763, 3425);

    /*public final String formatTime(final long time) {
        long s = time / 1000, m = s / 60, h = m / 60;
        s %= 60;
        m %= 60;
        h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
*/
    private int beginningXp;
    private int wcLevel;
    private int currentXp;
    private int xpGained;
    public int newLevelWC;
    private long timeBegan;
    private long timeRan;
    private int treesChopped;
    private String sourceImage = "URL HERE";
    //------------Cosmetics---------------
    final float paintTransparency = 0.60F; // 0.00F is transparent, 1.00F is opaque
    final Color textColor = new Color(255, 255, 255);
    final Color paintOutlineColor = new Color(0, 0, 0);
    final Color paintFillColor = new Color(0, 0, 0);
    final Color captionColor = new Color(139, 0, 0);
    final Color dividerColor = new Color(169, 169, 169);
    final Color progressBarFillColor = new Color(139, 0, 0);
    final Color progressBarOutlineColor = new Color(139, 0, 0);
    final Color warningColor = new Color(139, 0, 0);
    final String fontStyle = "SansSerif";
    private final Font baseFont = new Font(fontStyle, Font.PLAIN, baseFontSize);
    private final Font boldFont = new Font(fontStyle, Font.BOLD, baseFontSize);
    private final Font warningFont = new Font(fontStyle, Font.BOLD, 40);





    @Override
    public void onStart() throws InterruptedException {

        super.onStart();


        for (int i = 0; i < allSkills.length; i++) {
            startingLevelsAll[i] = getSkills().getStatic(allSkills[i]);
            startingExpAll[i] = getSkills().getExperience(allSkills[i]);
        }
        startTime = System.currentTimeMillis();

        paint = new PaintAPI();
        paint.exchangeContext(bot);

        getBot().addMessageListener(this);

        timeBegan = System.currentTimeMillis();

        walking.walk(yew);

        wcLevel = skills.getVirtualLevel(Skill.WOODCUTTING);
        beginningXp = skills.getExperience(Skill.WOODCUTTING);
        log("SCRIPT START: ");
        //getWalking().webWalk(Banks.GRAND_EXCHANGE);
        log("DOING INITAL BANKING");
        getBank().open();
        bank.depositAll();
        getBank().withdraw("Dragon axe", 1);
        bank.close();
        walking.webWalk(teak);


    }

    public void onMessage(Message m) {
        if (m.getMessage().contains("You get some teak logs")) {
            treesChopped++;
        }
    }

    @Override
    public int onLoop() throws InterruptedException {

        //Make sure this is at the very top of your OnLoop
        getBot().getCanvas().addMouseListener(listener);

        //int randomPitchStart = (int) (Math.random() * 110);
        //getCamera().movePitch(randomPitchStart);

        newLevelWC = skills.getVirtualLevel(Skill.WOODCUTTING);

        //walking.webWalk(yew);

        RS2Object tree = getObjects().closest("teak");
        if (getInventory().isFull()) {
            //getDepositBox().open();
            //getDepositBox().depositAllExcept();
            //getDepositBox().close();
            inventory.dropAllExcept("Dragon axe");
            //getWalking().webWalk(Banks.CATHERBY);
            //bank.open();
            //bank.depositAllExcept("Steele axe", "Bronze axe", "Iron axe", "Dragon axe", "Black axe", "Mithril axe", "Adamant axe", "Rune axe");
            //bank.close();
            sleep(random(780, 2300));
            //walking.webWalk(teak);
        } else if (tree != null && !myPlayer().isAnimating()) {
            //getCamera().toTop();
            getCamera().toEntity(tree);
            //int randomPitch = (int) (Math.random() * 180);
            //getCamera().movePitch(randomPitch);
            //int randomYaw = (int) (Math.random() * 180);
            //getCamera().moveYaw(randomYaw);
            walking.webWalk(teak);
            sleep(random(260, 3758));
            log("CHOPPING DOWN THE FUKEN TREE!");
            tree.interact("Chop down");
            sleep(random(700, 2970));
            getMouse().moveOutsideScreen();

            int randomNumber = (int) (Math.random() * 21);
            //getMouse().moveOutsideScreen();
            log(randomNumber);
            if (randomNumber <= 3) {
                mouse.move(578, 185);
                getMouse().click(false);
                mouse.move(706, 380);
                sleep(random(1800, 4000));
                mouse.move(644, 185);
                getMouse().click(false);
                sleep(random(800, 1400));
                //getCamera().toTop();
                getMouse().moveOutsideScreen();
            } else if (randomNumber == 5 || randomNumber == 6 || randomNumber == 7) {
                mouse.move(578, 185);
                mouse.click(false);
                mouse.move(703, 347);
                sleep(random(600, 2700));
                mouse.move(644, 185);
                getMouse().click(false);
                sleep(random(800, 1400));
                mouse.moveOutsideScreen();
            } else {
                mouse.moveOutsideScreen();
            }
            //{
            //getMouse().moveOutsideScreen();
            // }

        }
        return 0;

    }


    @Override
    public void onPaint(Graphics2D g) {
        Graphics2D gr = g;
        g.setColor(Color.RED);

        Point mP = getMouse().getPosition();

        List<RS2Object> allObjects = objects.getAll();
        List<RS2Object> instancesOfObject = new LinkedList<>();
        for (RS2Object i : allObjects) {
            if (i.getName().equals("Teak")) {
                paint.drawEntity(g, i, "Teak Tree", true, false, false, true, false, false, true);
            }
            if (i.getName().equals("Teak") && yew.contains(i)) {

                instancesOfObject.add(i);
            }
        }


        // Draw a line from top of screen (0), to bottom (500), with mouse x coordinate
        g.drawLine(mP.x, 0, mP.x, 500);

        // Draw a line from left of screen (0), to right (800), with mouse y coordinate
        g.drawLine(0, mP.y, 800, mP.y);

        g.drawLine(mP.x - 5, mP.y + 5, mP.x + 5, mP.y - 5);
        g.drawLine(mP.x + 5, mP.y + 5, mP.x - 5, mP.y - 5);

        timeRan = System.currentTimeMillis() - this.timeBegan;
        g.drawString(ft(timeRan), 10, 40);
        currentXp = skills.getExperience(Skill.WOODCUTTING);
        xpGained = currentXp - beginningXp;
        g.drawString("xp gained: " + xpGained, 10, 70);
        g.drawString("Time to level : " + formatTime(getExperienceTracker().getTimeToLevel(Skill.WOODCUTTING)), 10, 85);
        g.drawString("Starting woodcutting level: " + wcLevel, 10, 100);
        g.drawString("Current wc level: " + newLevelWC, 10, 115);
        g.drawString("LOGS CHOPPED: " + treesChopped, 10, 130);
        //RS2Object tree =
        //paint.drawEntity(g, tree, "Yew Tree", true, false, false, true, false, false, true);
        //public void drawEntity(Graphics2D g, Entity entity, String aString, boolean labelTile, boolean click, boolean cube, boolean minimap, boolean tile, boolean box, boolean wireframe)


        //weird paint from forums
        //Check for warning keywords in chat
        if (warningEnabled && checkChat(warningChatKeywords)) {
            showWarning = true;
        }

        // Timer Variables
        millis = (System.currentTimeMillis() - startTime);
        hours = (millis / 3600000L);
        millis -= hours * 3600000L;
        minutes = (millis / 60000L);
        millis -= minutes * 60000L;
        seconds = (millis / 1000L);

        ArrayList<Skill> paintSkills = new ArrayList<Skill>();
        ArrayList<Double> startingExp = new ArrayList<Double>();
        ArrayList<Integer> startingLvl = new ArrayList<Integer>();

        Graphics2D g2 = (Graphics2D) g;

        FontMetrics fmb = g2.getFontMetrics(baseFont);
        FontMetrics fmBold = g2.getFontMetrics(boldFont);
        FontMetrics fmWarning = g2.getFontMetrics(warningFont);
        FontRenderContext context = g2.getFontRenderContext();

        //Title Paint Dimensions
        titleRowHeight = fmb.getHeight() + textPadding;
        //if (customInfo != null)
        //    titlePaintHeight = titleRowHeight * (5 + customInfo.length) + textPadding;
        //else
            titlePaintHeight = titleRowHeight * 5 + textPadding;
        titlePaintX = 10 + skillsRowLength;
        titlePaintY = 480 - titlePaintHeight;
        Rectangle titlePaintRect = new Rectangle(titlePaintX, titlePaintY, titlePaintLength + 2 * textPadding, titlePaintHeight);

        //Rectangles for buttons & warning screen
        hideBtn = new Rectangle(titlePaintX + textPadding, titlePaintY + titlePaintHeight - titleRowHeight, lengthHide + 4, titleRowHeight - textPadding);
        resetBtn = new Rectangle(titlePaintX + 4 * textPadding + lengthHide, titlePaintY + titlePaintHeight - titleRowHeight, lengthReset + 4, titleRowHeight - textPadding);
        Rectangle warningRect = new Rectangle(0, 0, 520, 340);

        //Display only "Show" button if paint is hidden
        if (hidePaint) {
            g2.setColor(paintFillColor);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, paintTransparency));
            g2.fill(hideBtn);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    1.00f));
            g2.setColor(paintOutlineColor);
            g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(hideBtn);
            g2.setFont(boldFont);
            g2.setColor(dividerColor);
            g2.drawString(sshow, getCenterX(titlePaintX + textPadding, lengthHide + 4, sshow, fmBold),
                    getCenterY(titlePaintY + titlePaintHeight - titleRowHeight, titleRowHeight - textPadding, fmBold));
            if (boldFont.getStringBounds(sshow, context).getWidth() > lengthHide)
                lengthHide = (int) boldFont.getStringBounds(sshow, context).getWidth();
        } else {

            //Title Paint Fill & Draw
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, paintTransparency));
            if (showWarning) {
                g2.setColor(warningColor);
                g2.fill(warningRect);
            }
            g2.setColor(paintFillColor);
            g2.fill(titlePaintRect);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.00f));
            g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(paintOutlineColor);
            g2.draw(titlePaintRect);
            if (showWarning) {
                g2.setColor(Color.white);
                g2.setFont(warningFont);
                g2.drawString("Warning: Keyword in Chat", (520 - fmWarning.stringWidth("Warning: Keyword in Chat")) / 2, (fmWarning.getAscent() + (340 - (fmWarning.getAscent() + fmWarning.getDescent())) / 2));
            }

            //Add Hide and Reset Buttons
            g2.setColor(paintOutlineColor);
            g2.draw(hideBtn);
            g2.draw(resetBtn);
            g2.setFont(boldFont);
            g2.setColor(dividerColor);
            g2.drawString(shide, getCenterX(titlePaintX + textPadding, lengthHide + 4, shide, fmBold),
                    getCenterY(titlePaintY + titlePaintHeight - titleRowHeight, titleRowHeight - textPadding, fmBold));
            if (boldFont.getStringBounds(shide, context).getWidth() > lengthHide)
                lengthHide = (int) boldFont.getStringBounds(shide, context).getWidth();
            g2.drawString(reset, getCenterX(titlePaintX + 4 * textPadding + lengthHide, lengthReset + 4, reset, fmBold),
                    getCenterY(titlePaintY + titlePaintHeight - titleRowHeight, titleRowHeight - textPadding, fmBold));
            if (boldFont.getStringBounds(reset, context).getWidth() > lengthReset)
                lengthReset = (int) boldFont.getStringBounds(reset, context).getWidth();

            //Add Title
            g2.setColor(captionColor);
            g2.setFont(boldFont);
            g2.drawString(title, getCenterX(titlePaintX + textPadding, titlePaintLength, title, fmBold), getCenterY(titlePaintY + textPadding, titleRowHeight, fmBold));
            if (boldFont.getStringBounds(title, context).getWidth() + 2 * textPadding > titlePaintLength)
                titlePaintLength = (int) boldFont.getStringBounds(title, context).getWidth() + 2 * textPadding;

            //Add text
            ArrayList<String> textTitlePaint = new ArrayList<String>();
            textTitlePaint.add("Time Ran: " + hours + " hours " + minutes + " minutes " + seconds + " seconds");
            //if (customInfo != null) {
            //    for (int i = 0; i < customInfo.length; i++) {
            //        textTitlePaint.add(customInfo[i]);
            //    }
           // }
            textTitlePaint.add("World " + getWorlds().getCurrentWorld() + " | " + (getMap().getPlayers().getAll().size() - 1) + " players on map");

            g2.setColor(textColor);
            g2.setFont(baseFont);
            for (int i = 0; i < textTitlePaint.size(); i++) {
                g2.drawString(textTitlePaint.get(i), getCenterX(titlePaintX + textPadding, titlePaintLength, textTitlePaint.get(i), fmb), getCenterY(titlePaintY + textPadding, titleRowHeight, fmb) + (1 + i) * titleRowHeight);
                if (baseFont.getStringBounds(textTitlePaint.get(i), context).getWidth() > titlePaintLength)
                    titlePaintLength = (int) baseFont.getStringBounds(textTitlePaint.get(i), context).getWidth();
            }
            g2.setColor(captionColor);
            String signature = "Created with DylanSRT Paint Template";
            g2.drawString(signature, getCenterX(titlePaintX + textPadding, titlePaintLength, signature, fmb), getCenterY(titlePaintY + titlePaintHeight - titleRowHeight, titleRowHeight - textPadding, fmBold) - titleRowHeight);

            //Check for an experience gain in any skill
            for (int i = 0; i < allSkills.length; i++) {
                Skill s = allSkills[i];
                if (skills.getExperience(s) != startingExpAll[i]) {
                    paintSkills.add(s);
                    startingLvl.add((int) startingLevelsAll[i]);
                    startingExp.add(startingExpAll[i]);
                }
            }
            l = paintSkills.size();

            //If experience has been gained in any skill, skills paint is initialized
            if (l > 0) {
                //Skills Paint Dimensions
                rowHeight = fmb.getHeight() + 2 * textPadding;
                skillsPaintHeight = (l + 1) * rowHeight;
                skillsPaintX = 10;
                skillsPaintY = 480 - skillsPaintHeight;
                Rectangle skillsPaintRect = new Rectangle(skillsPaintX, skillsPaintY, skillsRowLength, skillsPaintHeight);
                rowY = new int[l];
                skillRectangles = new Rectangle[l];
                progressRectangles = new Rectangle[l];

                //Calculate data for skills paint
                skillsPaintData = new String[l + 1][6];
                String[] skillsPaintCaptions = {"Skill", "LVL", "kXP", "kXP/hr", "%TNL", "TimeTNL"};
                for (int i = 0; i < 6; i++) {
                    skillsPaintData[0][i] = skillsPaintCaptions[i];
                }
                for (int i = 1; i < l + 1; i++) {
                    Skill s = paintSkills.get(i - 1);
                    skillsPaintData[i][0] = s.name();
                    skillsPaintData[i][1] = getSkills().getStatic(s) + "/" + startingLvl.get(i - 1);
                    double xpGained = getSkills().getExperience(s) - startingExp.get(i - 1);
                    DecimalFormat df = new DecimalFormat("###.##");
                    skillsPaintData[i][2] = df.format(xpGained / 1000) + "";
                    double xpPhr = ((int) (xpGained * 3600000.0D / (System.currentTimeMillis() - startTime)));
                    skillsPaintData[i][3] = df.format(xpPhr / 1000) + "";
                    skillsPaintData[i][4] = Double.toString((100 * (experiencePrevLevel(s) / experienceTotalNeeded(s)))).substring(0, 4);
                    skillsPaintData[i][5] = formatTime(timeTnl(experienceToNextLevel(s), xpPhr));
                }
                for (int i = 0; i < l; i++) {
                    Skill s = paintSkills.get(i);
                    rowY[i] = (skillsPaintY + (i + 1) * rowHeight);
                    skillRectangles[i] = new Rectangle(skillsPaintX, rowY[i], skillsRowLength, rowHeight);
                    progressRectangles[i] = (new Rectangle(skillsPaintX, rowY[i] + 2, 0, rowHeight - 4));
                    progressRectangles[i].setSize((int) ((100 * (experiencePrevLevel(s) / experienceTotalNeeded(s))) * skillsRowLength / 100), rowHeight - 4);
                }

                //Add transparent paint background
                g2.setColor(paintFillColor);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, paintTransparency));
                g2.fill(skillsPaintRect);

                //Add progress bars & progress bar outline
                g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                for (int i = 0; i < l; i++) {
                    g2.setColor(progressBarFillColor);
                    g2.fill(progressRectangles[i]);
                    g2.setColor(progressBarOutlineColor);
                    g2.draw(progressRectangles[i]);
                }

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                        1.00f));

                //Add lines to seperate skills
                g2.setColor(paintOutlineColor);
                for (int i = 0; i < l; i++) {
                    g2.drawLine(skillsPaintX, rowY[i], skillsPaintX + skillsRowLength, rowY[i]);
                }

                //Draw thick line around paint
                g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.draw(skillsPaintRect);
                g2.draw(titlePaintRect);

                //Arrays to keep track of string lengths for dynamic window size optimization
                int[] x = {skillsPaintX,
                        skillsPaintX + lengthC1,
                        skillsPaintX + lengthC1 + lengthC2,
                        skillsPaintX + lengthC1 + lengthC2 + lengthC3,
                        skillsPaintX + lengthC1 + lengthC2 + lengthC3 + lengthC4,
                        skillsPaintX + lengthC1 + lengthC2 + lengthC3 + lengthC4 + lengthC5};
                int[] lengths = {lengthC1, lengthC2, lengthC3, lengthC4, lengthC5, lengthC6};
                sd = "|";

                //Add Skill Paint Captions
                g2.setFont(boldFont);
                g2.setColor(captionColor);
                for (int i = 0; i < 6; i++) {
                    g2.drawString(skillsPaintData[0][i], getCenterX(x[i] + i * lengthDivide + (1 + 2 * i) * textPadding, lengths[i], skillsPaintData[0][i], fmBold), getCenterY(skillsPaintY, rowHeight, fmBold));
                    if (boldFont.getStringBounds(skillsPaintData[0][i], context).getWidth() > lengths[i]) {
                        lengths[i] = (int) boldFont.getStringBounds(skillsPaintData[0][i], context).getWidth();

                    }
                }

                //Add dividers
                g2.setColor(dividerColor);
                context = g2.getFontRenderContext();
                for (int i = 0; i < l + 1; i++) {
                    for (int j = 0; j < 5; j++) {
                        g2.drawString(sd, getCenterX(x[j + 1] + j * lengthDivide + (2 + 2 * j) * textPadding, lengthDivide, sd, fmBold), getCenterY(skillsPaintY + rowHeight * i, rowHeight, fmBold));
                        lengthDivide = (int) boldFont.getStringBounds(sd, context).getWidth();
                    }
                }

                //Add data
                g2.setFont(baseFont);
                g2.setColor(textColor);
                for (int i = 1; i < l + 1; i++) {
                    for (int j = 0; j < 6; j++) {
                        g2.drawString(skillsPaintData[i][j], getCenterX(x[j] + j * lengthDivide + (1 + 2 * j) * textPadding, lengths[j], skillsPaintData[i][j], fmb), getCenterY(skillsPaintY + rowHeight * i, rowHeight, fmb));
                        if (baseFont.getStringBounds(skillsPaintData[i][j], context).getWidth() > lengths[j]) {
                            lengths[j] = (int) baseFont.getStringBounds(skillsPaintData[i][j], context).getWidth();
                        }
                    }
                }

                //Update lengths
                lengthC1 = lengths[0];
                lengthC2 = lengths[1];
                lengthC3 = lengths[2];
                lengthC4 = lengths[3];
                lengthC5 = lengths[4];
                lengthC6 = lengths[5];
                skillsRowLength = lengthC1 + lengthC2 + lengthC3 + lengthC4 + lengthC5 + lengthC6 + lengthDivide * 5 + textPadding * 12;
            }

        }
    }


    private String ft(long duration) {
        String res = "";
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
        if (days == 0) {
            res = (hours + ":" + minutes + ":" + seconds);
        } else {
            res = (days + ":" + hours + ":" + minutes + ":" + seconds);
        }
        return res;
    }
    MouseListener listener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            p = mouse.getPosition();
            if (hideBtn.contains(p)) {
                hidePaint = !hidePaint;
            } else if (resetBtn.contains(p)) {
                for (int i = 0; i < allSkills.length; i++) {
                    startingLevelsAll[i] = getSkills().getStatic(allSkills[i]);
                    startingExpAll[i] = getSkills().getExperience(allSkills[i]);
                }
                startTime = System.currentTimeMillis();
                skillsRowLength = 0;
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    };

    private int getCenterX(int x, int w, String s, FontMetrics f) {
        return x + (w - f.stringWidth(s)) / 2;
    }

    private int getCenterY(int y, int h, FontMetrics f) {
        return y + (f.getAscent() + (h - (f.getAscent() + f.getDescent())) / 2);
    }

    private boolean checkChat(String[] keywords) {
        for (String s : keywords) {
            if (chatbox.contains(Chatbox.MessageType.ALL, s)) {
                return true;
            }
        }
        return false;
    }

    private int experienceToNextLevel(Skill skill) {
        int xp = getSkills().getExperience(skill);
        for (int i = 0; i < 99; i++) {
            if (xp < xpForLevels[i]) {
                return xpForLevels[i] - xp;
            }
        }
        return 200000000 - xp;
    }

    private double experiencePrevLevel(Skill skill) {
        int xp = getSkills().getExperience(skill);
        for (int i = 0; i < 99; i++) {
            if (xp < xpForLevels[i]) {
                return xp - xpForLevels[i - 1];
            }
        }
        return 200000000 - xp;
    }

    private double experienceTotalNeeded(Skill skill) {
        int xp = getSkills().getExperience(skill);
        for (int i = 0; i < 99; i++) {
            if (xp < xpForLevels[i]) {
                return xpForLevels[i] - xpForLevels[i - 1];
            }
        }
        return 200000000 - xp;
    }

    long timeTnl(double xpTNL, double xpPH) {

        if (xpPH > 0) {
            long timeTNL = (long) ((xpTNL / xpPH) * 3600000.0D);
            return timeTNL;
        }
        return 0;
    }


    private String formatTime(long time) {
        int sec = (int) (time / 1000L), d = sec / 86400, h = sec / 3600, m = sec / 60 % 60, s = sec % 60;
        return new StringBuilder()
                .append(h < 10 ? new StringBuilder().append("0").append(h)
                        .toString() : Integer.valueOf(h))
                .append(":")
                .append(m < 10 ? new StringBuilder().append("0").append(m)
                        .toString() : Integer.valueOf(m))
                .append(":")
                .append(s < 10 ? new StringBuilder().append("0").append(s)
                        .toString() : Integer.valueOf(s)).toString();
    }

    int skillsRowLength, titlePaintLength, rowHeight, skillsPaintHeight, titlePaintHeight, skillsPaintX, titlePaintX, skillsPaintY,
            titlePaintY, lengthC1, lengthC2, lengthC3, lengthC4, lengthC5, lengthC6, lengthDivide, lengthHide, lengthReset,
            titleRowHeight,l;
    int[] rowY;
    int[] xpForLevels = {0, 83, 174, 276, 388, 512, 650, 801, 969, 1154,
            1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523, 3973, 4470,
            5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363,
            14833, 16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648,
            37224, 41171, 45529, 50339, 55649, 61512, 67983, 75127, 83014,
            91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040,
            203254, 224466, 247886, 273742, 302288, 333804, 368599, 407015,
            449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257,
            992895, 1096278, 1210421, 1336443, 1475581, 1629200, 1798808,
            1986068, 2192818, 2421087, 2673114, 2951373, 3258594, 3597792,
            3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629,
            7944614, 8771558, 9684577, 10692629, 11805606, 13034431};

    String state, sd;
    String shide = "Hide";
    String sshow = "Show";
    String reset = "Reset";
    String[][] skillsPaintData;

    double[] startingLevelsAll = new double[23];
    double[] startingExpAll = new double[23];

    public long startTime = 0L, millis = 0L, hours = 0L;
    public long minutes = 0L, seconds = 0L, last = 0L;

    Rectangle hideBtn, resetBtn;
    Rectangle[] skillRectangles, progressRectangles;

    boolean hidePaint = false;
    boolean showWarning = false;

    final Skill[] allSkills = {Skill.ATTACK, Skill.MINING, Skill.SLAYER, Skill.HITPOINTS, Skill.DEFENCE, Skill.MAGIC, Skill.RANGED,
            Skill.STRENGTH, Skill.AGILITY, Skill.CONSTRUCTION, Skill.COOKING, Skill.CRAFTING, Skill.FARMING, Skill.FIREMAKING, Skill.FISHING,
            Skill.FLETCHING, Skill.HERBLORE, Skill.HUNTER, Skill.PRAYER, Skill.RUNECRAFTING, Skill.SMITHING, Skill.THIEVING, Skill.WOODCUTTING};

    Point p;


    @Override
    public void onExit() throws InterruptedException{
        log("SCRIPT END: ");
        super.onExit();

    }

}


