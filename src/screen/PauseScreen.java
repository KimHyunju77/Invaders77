package screen;

import java.awt.event.KeyEvent;

import engine.Cooldown;
import engine.Core;
import engine.GameState;


public class PauseScreen<selectionCooldown> extends Screen {

	/** Milliseconds between changes in user selection. */
	private static final int SELECTION_TIME = 200;
	private final Cooldown selectionCooldown;


	public PauseScreen (final int width, final int height, final int fps) {
		super(width, height, fps);

		// Defaults to pause.
		this.returnCode = 2; // pause -> main menu 대체
		this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
		this.selectionCooldown.reset();
	}

	public final int run(){
		super.run();

		return this.returnCode;
	}

	protected final void update(){
		super.update();

		draw();

		/**고르기*/
		if (this.selectionCooldown.checkFinished()
				&& this.inputDelay.checkFinished()) {
			if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
					|| inputManager.isKeyDown(KeyEvent.VK_S)) {
				nextCheckOption();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_UP)
					|| inputManager.isKeyDown(KeyEvent.VK_W)){
				previousCheckOption();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_SPACE))
				this.isRunning = false;
		}
	}

	/**다음 옵션*/
	private void nextCheckOption() {
		if(this.returnCode == 2)
			this.returnCode = 1;
	}

	/**그전 선택지*/
	private void previousCheckOption() {
		if (this.returnCode == 1)
			this.returnCode = 2;
	}


	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw(){
		/**화면 초기화*/
		drawManager.initDrawing(this);

		GameState gameState = new GameState();


		/**그리기*/


		drawManager.drawPauseScreen(this, gameState.getScore());
		drawManager.drawPauseCheck(this, this.returnCode);

		/**다 그렸다고 업데이트*/
		drawManager.completeDrawing(this);
	}

}


