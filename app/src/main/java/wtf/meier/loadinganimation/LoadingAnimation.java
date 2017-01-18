package wtf.meier.loadinganimation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by meier on 17/01/2017.
 */

public class LoadingAnimation extends View {

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            degree = (degree + 2) % 360;
            invalidate();
        }
    };

    private int degree = 180;

    private static final float ROTATE_TIME_MILLIS = 100;
    private Paint circlePaint;
    private Bitmap bitmap;
    private Bitmap parentBitmap;
    private Matrix matrix = new Matrix();

    public LoadingAnimation(Context context) {
        super(context);
        setUp();
    }

    public LoadingAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public LoadingAnimation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoadingAnimation(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUp();
    }

    public void setUp() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        parentBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bike);
//        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        PointF center = new PointF((getWidth() / 2), (getHeight() / 2));
        bitmap = Bitmap.createScaledBitmap(
                parentBitmap,
                getWidth() / 5,
                getHeight() / 5,
                false
        );
        canvas.drawCircle(
                getWidth() / 2,
                getHeight() / 2,
                Math.min(getHeight(), getHeight()) / 8,
                circlePaint
        );
        PointF position = getPosition(center, Math.min(getHeight(), getHeight()) / 8 + 10 + bitmap.getHeight() / 8 + 10, degree);
        matrix.reset();
        matrix.postTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2); // Centers image
        matrix.postRotate(degree + 90);
        matrix.postTranslate(position.x, position.y);
        canvas.drawBitmap(bitmap, matrix, null);

        postDelayed(runnable, 10);

    }

    private PointF getPosition(PointF center, float radius, float angle) {

        PointF p = new PointF((float) (center.x + radius * Math.cos(Math.toRadians(angle))),
                (float) (center.y + radius * Math.sin(Math.toRadians(angle))));

        return p;
    }
}
