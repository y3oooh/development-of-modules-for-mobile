package com.example.modulsapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.modulsapp.R;


public class TariffFillView extends View {

    private static final float DEFAULT_SIZE_DP = 168f;
    private static final float RING_STROKE_DP = 16f;
    private static final float MAIN_TEXT_SP = 26f;
    private static final float SUB_TEXT_SP = 12f;

    private final Paint paintTrack;
    private final Paint paintFillArc;
    private final Paint paintSavingArc;
    private final Paint paintMainText;
    private final Paint paintSubText;
    private final RectF ringRect = new RectF();

    private final float ringStrokePx;
    private final float defaultSizePx;

    private float centerX = 0f;
    private float centerY = 0f;
    private float mainTextBaselineY = 0f;
    private float subTextBaselineY = 0f;

    private float fillPercent = 0f;
    private float savingPercent = 0f;

    private String mainText = "0%";
    private String subText = "от бюджета";

    public TariffFillView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        float density = context.getResources().getDisplayMetrics().density;
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        ringStrokePx = RING_STROKE_DP * density;
        defaultSizePx = DEFAULT_SIZE_DP * density;

        int colorTrack = 0xFFE7E5E4;
        int colorFill = 0xFF0F766E;
        int colorSaving = 0xFFF59E0B;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TariffFillView);
            fillPercent = a.getFloat(R.styleable.TariffFillView_fillPercent, 0f);
            savingPercent = a.getFloat(R.styleable.TariffFillView_savingPercent, 0f);
            colorTrack = a.getColor(R.styleable.TariffFillView_trackColor, colorTrack);
            colorFill = a.getColor(R.styleable.TariffFillView_ringColor, colorFill);
            colorSaving = a.getColor(R.styleable.TariffFillView_savingColor, colorSaving);
            a.recycle();
        }

        paintTrack = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTrack.setStyle(Paint.Style.STROKE);
        paintTrack.setStrokeCap(Paint.Cap.ROUND);
        paintTrack.setStrokeWidth(ringStrokePx);
        paintTrack.setColor(colorTrack);

        paintFillArc = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFillArc.setStyle(Paint.Style.STROKE);
        paintFillArc.setStrokeCap(Paint.Cap.ROUND);
        paintFillArc.setStrokeWidth(ringStrokePx);
        paintFillArc.setColor(colorFill);

        paintSavingArc = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSavingArc.setStyle(Paint.Style.STROKE);
        paintSavingArc.setStrokeCap(Paint.Cap.ROUND);
        paintSavingArc.setStrokeWidth(ringStrokePx);
        paintSavingArc.setColor(colorSaving);

        paintMainText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintMainText.setColor(0xFF1C1917);
        paintMainText.setTextSize(MAIN_TEXT_SP * scaledDensity);
        paintMainText.setTextAlign(Paint.Align.CENTER);
        paintMainText.setFakeBoldText(true);

        paintSubText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSubText.setColor(0xFF78716C);
        paintSubText.setTextSize(SUB_TEXT_SP * scaledDensity);
        paintSubText.setTextAlign(Paint.Align.CENTER);

        rebuildTexts();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desired = Math.round(defaultSizePx);
        int size = Math.min(
                resolveSize(desired, widthMeasureSpec),
                resolveSize(desired, heightMeasureSpec)
        );
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int size = Math.min(w, h);
        float inset = ringStrokePx / 2f + getPaddingLeft();
        ringRect.set(inset, inset, size - inset, size - inset);

        centerX = size / 2f;
        centerY = size / 2f;
        mainTextBaselineY = centerY - 4f;
        subTextBaselineY = centerY + paintSubText.getTextSize() + 6f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(ringRect, 0f, 360f, false, paintTrack);

        float fillSweep = 360f * (fillPercent / 100f);
        canvas.drawArc(ringRect, -90f, fillSweep, false, paintFillArc);

        if (savingPercent > 0f && fillSweep > 0f) {
            float savingSweep = Math.min(fillSweep, fillSweep * (savingPercent / 100f));
            float savingStart = -90f + fillSweep - savingSweep;
            canvas.drawArc(ringRect, savingStart, savingSweep, false, paintSavingArc);
        }

        canvas.drawText(mainText, centerX, mainTextBaselineY, paintMainText);
        canvas.drawText(subText, centerX, subTextBaselineY, paintSubText);
    }

    public void setData(float fillPercent, float savingPercent) {
        float clampedFill = Math.max(0, Math.min(100, fillPercent));
        float clampedSaving = Math.max(0, Math.min(100, savingPercent));

        if (clampedFill == this.fillPercent && clampedSaving == this.savingPercent) {
            return;
        }

        this.fillPercent = clampedFill;
        this.savingPercent = clampedSaving;
        rebuildTexts();
        invalidate();
    }

    private void rebuildTexts() {
        this.mainText = String.format("%.0f%%", fillPercent);
        this.subText = savingPercent > 0f
                ? String.format("экономия %.0f%%", savingPercent)
                : "от бюджета";
    }
}