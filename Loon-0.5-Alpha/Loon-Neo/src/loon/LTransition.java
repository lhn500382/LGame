/**
 * Copyright 2008 - 2011
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loon
 * @author cping
 * @email：javachenpeng@yahoo.com
 * @version 0.1
 */
package loon;

import loon.action.map.Config;
import loon.action.sprite.effect.ArcEffect;
import loon.action.sprite.effect.CrossEffect;
import loon.action.sprite.effect.FadeEffect;
import loon.action.sprite.effect.FadeOvalEffect;
import loon.action.sprite.effect.PixelWindEffect;
import loon.action.sprite.effect.SplitEffect;
import loon.canvas.LColor;
import loon.opengl.GLEx;
import loon.opengl.TextureUtils;
import loon.utils.MathUtils;

/**
 * 自0.3.2版起新增的Screen切换过渡效果类，内置有多种过渡特效。
 * 
 * example:
 * 
 * public class Sample extends Screen{
 * 
 * ......
 * 
 * public LTransition onTransition(){ return xxx(method) } }
 * 
 * 
 */
public class LTransition {

	/**
	 * 随机的百叶窗特效
	 * 
	 * @return
	 */
	public static final LTransition newCrossRandom() {
		return newCrossRandom(LColor.black);
	}

	/**
	 * 百叶窗特效
	 * 
	 * @param c
	 * @return
	 */
	public static final LTransition newCrossRandom(LColor c) {
		return newCross(MathUtils.random(0, 1), TextureUtils.createTexture(
				LSystem.viewSize.getWidth(), LSystem.viewSize.getHeight(), c));
	}

	/**
	 * 百叶窗特效
	 * 
	 * @param c
	 * @return
	 */
	public static final LTransition newCross(final int c, final LTexture texture) {

		if (LSystem._base != null) {

			final LTransition transition = new LTransition();

			transition.setTransitionListener(new TransitionListener() {

				final CrossEffect cross = new CrossEffect(c, texture);

				public void draw(GLEx g) {
					cross.createUI(g);
				}

				public void update(long elapsedTime) {
					cross.update(elapsedTime);
				}

				public boolean completed() {
					return cross.isCompleted();
				}

				public void close() {
					cross.close();
				}

			});
			transition.setDisplayGameUI(true);
			transition.code = 1;
			return transition;
		}
		return null;
	}

	/**
	 * 默认使用黑色的圆弧渐变特效
	 * 
	 * @return
	 */
	public static final LTransition newArc() {
		return newArc(LColor.black);
	}

	/**
	 * 单一色彩的圆弧渐变特效
	 * 
	 * @return
	 */
	public static final LTransition newArc(final LColor c) {

		if (LSystem._base != null) {

			final LTransition transition = new LTransition();

			transition.setTransitionListener(new TransitionListener() {

				final ArcEffect arc = new ArcEffect(c);

				public void draw(GLEx g) {
					arc.createUI(g);
				}

				public void update(long elapsedTime) {
					arc.update(elapsedTime);
				}

				public boolean completed() {
					return arc.isCompleted();
				}

				public void close() {
					arc.close();
				}

			});
			transition.setDisplayGameUI(true);
			transition.code = 1;
			return transition;
		}
		return null;
	}

	/**
	 * 产生一个Screen画面向双向分裂的过渡特效
	 * 
	 * @param texture
	 * @return
	 */
	public static final LTransition newSplitRandom(LTexture texture) {
		return newSplit(MathUtils.random(0, Config.TDOWN), texture);
	}

	/**
	 * 产生一个Screen画面向双向分裂的过渡特效
	 * 
	 * @param c
	 * @return
	 */
	public static final LTransition newSplitRandom(LColor c) {
		return newSplitRandom(TextureUtils.createTexture(
				LSystem.viewSize.getWidth(), LSystem.viewSize.getHeight(), c));
	}

	/**
	 * 产生一个Screen画面向双向分裂的过渡特效(方向的静态值位于Config类中)
	 * 
	 * @param d
	 * @param texture
	 * @return
	 */
	public static final LTransition newSplit(final int d, final LTexture texture) {

		if (LSystem._base != null) {

			final LTransition transition = new LTransition();

			transition.setTransitionListener(new TransitionListener() {

				final SplitEffect split = new SplitEffect(texture, d);

				public void draw(GLEx g) {
					split.createUI(g);
				}

				public void update(long elapsedTime) {
					split.update(elapsedTime);
				}

				public boolean completed() {
					return split.isCompleted();
				}

				public void close() {
					split.close();
				}

			});
			transition.setDisplayGameUI(true);
			transition.code = 1;
			return transition;
		}
		return null;
	}

	/**
	 * 产生一个黑色的淡入效果
	 * 
	 * @return
	 */
	public static final LTransition newFadeIn() {
		return newFade(FadeEffect.TYPE_FADE_IN);
	}

	/**
	 * 产生一个黑色的淡出效果
	 * 
	 * @return
	 */
	public static final LTransition newFadeOut() {
		return newFade(FadeEffect.TYPE_FADE_OUT);
	}

	/**
	 * 产生一个黑色的淡入/淡出效果
	 * 
	 * @param type
	 * @return
	 */
	public static final LTransition newFade(int type) {
		return newFade(type, LColor.black);
	}

	/**
	 * 产生一个指定色彩的淡入效果
	 * 
	 * @param c
	 * @return
	 */
	public static final LTransition newFade(final int type, final LColor c) {
		if (LSystem._base != null) {
			final LTransition transition = new LTransition();

			transition.setTransitionListener(new TransitionListener() {

				final FadeEffect fade = FadeEffect.getInstance(type, c);

				public void draw(GLEx g) {
					fade.createUI(g);
				}

				public void update(long elapsedTime) {
					fade.update(elapsedTime);
				}

				public boolean completed() {
					return fade.isCompleted();
				}

				public void close() {
					fade.close();
				}

			});
			transition.setDisplayGameUI(true);
			transition.code = 1;
			return transition;
		}
		return null;
	}

	/**
	 * 产生一个黑色的淡入效果
	 * 
	 * @return
	 */
	public static final LTransition newFadeOvalIn(LColor c) {
		return newOvalFade(FadeEffect.TYPE_FADE_IN, c);
	}

	/**
	 * 产生一个黑色的淡出效果
	 * 
	 * @return
	 */
	public static final LTransition newFadeOvalOut(LColor c) {
		return newOvalFade(FadeEffect.TYPE_FADE_OUT, c);
	}

	public static final LTransition newOvalFade(final int type, final LColor c) {
		if (LSystem._base != null) {
			final LTransition transition = new LTransition();

			transition.setTransitionListener(new TransitionListener() {

				final FadeOvalEffect ovalEffect = new FadeOvalEffect(type, c);

				public void draw(GLEx g) {
					ovalEffect.createUI(g);
				}

				public void update(long elapsedTime) {
					ovalEffect.update(elapsedTime);
				}

				public boolean completed() {
					return ovalEffect.isCompleted();
				}

				public void close() {
					ovalEffect.close();
				}

			});
			transition.setDisplayGameUI(true);
			transition.code = 1;
			return transition;
		}
		return null;
	}

	public static final LTransition newPixelWind(final LColor c) {
		if (LSystem._base != null) {
			final LTransition transition = new LTransition();

			transition.setTransitionListener(new TransitionListener() {

				final PixelWindEffect windEffect = new PixelWindEffect(c);

				public void draw(GLEx g) {
					windEffect.createUI(g);
				}

				public void update(long elapsedTime) {
					windEffect.update(elapsedTime);
				}

				public boolean completed() {
					return windEffect.isCompleted();
				}

				public void close() {
					windEffect.close();
				}

			});
			transition.setDisplayGameUI(true);
			transition.code = 1;
			return transition;
		}
		return null;
	}
	
	public static final LTransition newEmpty() {

		final LTransition transition = new LTransition();

		transition.setTransitionListener(new TransitionListener() {

			public void draw(GLEx g) {
			}

			public void update(long elapsedTime) {
			}

			public boolean completed() {
				return true;
			}

			public void close() {
			}

		});

		transition.setDisplayGameUI(true);
		transition.code = 1;
		return transition;

	}

	public static interface TransitionListener extends LRelease {

		public void update(long elapsedTime);

		public void draw(GLEx g);

		public boolean completed();

		public void close();
	}

	// 是否在在启动过渡效果同时显示游戏画面（即是否顶层绘制过渡画面，底层同时绘制标准游戏画面）
	boolean isDisplayGameUI;

	int code;

	TransitionListener listener;

	public void setDisplayGameUI(boolean s) {
		this.isDisplayGameUI = s;
	}

	public boolean isDisplayGameUI() {
		return this.isDisplayGameUI;
	}

	public void setTransitionListener(TransitionListener l) {
		this.listener = l;
	}

	public TransitionListener getTransitionListener() {
		return this.listener;
	}

	final void update(long elapsedTime) {
		if (listener != null) {
			listener.update(elapsedTime);
		}
	}

	final void draw(GLEx g) {
		if (listener != null) {
			listener.draw(g);
		}
	}

	final boolean completed() {
		if (listener != null) {
			return listener.completed();
		}
		return false;
	}

	final void close() {
		if (listener != null) {
			listener.close();
		}
	}
}
