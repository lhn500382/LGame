package loon.action.sprite.effect;

import loon.LSystem;
import loon.canvas.LColor;
import loon.opengl.GLEx;
import loon.utils.MathUtils;

public class PixelSnowEffect extends PixelBaseEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private float t_x, t_y;

	private TriangleEffect first;

	private TriangleEffect[] force;

	private TriangleEffect[] dif;

	private LColor[] colors;

	private boolean onlySnow = false;

	public void setOnlySnow(boolean flag) {
		this.onlySnow = flag;
	}

	public boolean getOnlySnow() {
		return onlySnow;
	}

	private final float[][] fdelta = { { 0.0f, 3f },
			{ 2.3999999999999999f, -1.5f }, { -2.3999999999999999f, -1.5f } };

	public PixelSnowEffect(LColor color) {
		this(color, 0, 0, LSystem.viewSize.getWidth() / 2, LSystem.viewSize
				.getHeight() / 2);
	}

	public PixelSnowEffect(LColor color, float x, float y, float w, float h) {
		super(color, x, y, w, h);
		this.t_x = x;
		this.t_y = y;
		this.first = new TriangleEffect(w, h, fdelta, 0.0f, 0.0f, 3f);
		float[][] vector = { { 8f, 0.0f }, { -4f, 6f }, { -4f, -6f } };
		this.force = new TriangleEffect[32];
		for (int j = 0; j < force.length; j++) {
			float nx = MathUtils.random(200) - 100;
			nx /= 45f;
			float ny = MathUtils.random(200) - 100;
			ny /= 45f;
			force[j] = new TriangleEffect(w, h, vector, nx, ny, 12f);
		}
		float res[][] = { { 32f, 0.0f }, { -16f, 24f }, { -16f, -24f } };
		dif = new TriangleEffect[32];
		colors = new LColor[32];
		for (int j = 0; j < dif.length; j++) {
			float d1 = MathUtils.random(9000);
			d1 /= 155f;
			float nx = MathUtils.random(8000) + 2000;
			float ny = MathUtils.random(8000) + 2000;
			nx /= 155f;
			ny /= 155f;
			nx *= MathUtils.cos((d1 * 3.1415926535897931f) / 180f);
			ny *= MathUtils.sin((d1 * 3.1415926535897931f) / 180f);
			if (MathUtils.random(2) == 1) {
				nx *= -1f;
			}
			if (MathUtils.random(2) == 1) {
				ny *= -1f;
			}
			nx /= 25f;
			ny /= 25f;
			dif[j] = new TriangleEffect(w, h, res, nx, ny,
					MathUtils.random(30) + 3);
			int r = MathUtils.random(64) + 192;
			colors[j] = new LColor((int) (color.r * r), (int) (color.g * r),
					(int) (color.b * r), color.getAlpha());
		}
		this.limit = 160;
		triangleEffects.add(force);
		triangleEffects.add(dif);
		triangleEffects.add(new TriangleEffect[] { first });
		setDelay(0);
		setEffectDelay(0);
	}

	@Override
	public void draw(GLEx g, float tx, float ty) {
		if (super.complete) {
			return;
		}
		float x = t_x - tx;
		float y = t_y - ty;
		int tmp = g.color();
		g.setColor(color);
		if (onlySnow) {
			for (int i = 0; i < dif.length; i++) {
				g.setColor(colors[i]);
				dif[i].drawPaint(g, x, y);
			}
		} else {
			if (super.frame < 120) {
				float[][] delta = first.getDelta();
				for (int j = 0; j < delta.length; j++) {
					for (int i = 0; i < delta[j].length; i++) {
						delta[j][i] += fdelta[j][i] / 45f;
					}
				}
				first.setDelta(delta);
				first.resetAverage();
				first.drawPaint(g, x, y);
				for (int j = 0; j < super.frame * 2 && j < force.length; j++) {
					force[j].drawPaint(g, x, y);
				}
			} else if (super.frame < 240) {
				for (int i = 0; i < dif.length; i++) {
					g.setColor(colors[i]);
					dif[i].drawPaint(g, x, y);
				}
			}
		}
		g.setColor(tmp);
		if (super.frame >= limit) {
			super.complete = true;
		}
	}

}
