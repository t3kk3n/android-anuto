package ch.bfh.anuto;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.io.InputStream;

import ch.bfh.anuto.game.data.Level;
import ch.bfh.anuto.game.objects.BasicTower;
import ch.bfh.anuto.game.objects.LaserTower;
import ch.bfh.anuto.game.objects.AreaTower;
import ch.bfh.anuto.game.objects.RocketTower;
import ch.bfh.anuto.game.GameEngine;


public class TowerDefenseView extends View implements GameEngine.Listener {
    private final static String TAG = TowerDefenseView.class.getName();

    protected GameEngine mGame;

    public TowerDefenseView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //getHolder().addCallback(this);
        setFocusable(true);

        try {
            InputStream inStream = getResources().openRawResource(R.raw.level1);
            Level lvl = Level.deserialize(inStream);

            mGame = lvl.createGame(getResources());
            mGame.addListener(this);

            mGame.addObject(new BasicTower(new PointF(5, 4)));
            mGame.addObject(new LaserTower(new PointF(6, 5)));

            mGame.addObject(new AreaTower(new PointF(5, 9)));
            mGame.addObject(new RocketTower(new PointF(2, 8)));

            lvl.startWave(mGame, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameEngine getGame() {
        return mGame;
    }

    public void start() {
        mGame.start();
    }

    public void stop() {
        mGame.stop();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mGame.setScreenSize(w, h);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mGame.render(canvas);
    }

    @Override
    public void onRenderRequest() {
        postInvalidate();
    }
}
