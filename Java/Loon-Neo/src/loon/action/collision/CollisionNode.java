/**
 * Copyright 2008 - 2019 The Loon Game Engine Authors
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
 * @version 0.5
 */
package loon.action.collision;

public class CollisionNode {

	private CollisionObject actor;

	private BSPCollisionNode node;

	private CollisionNode next;

	private CollisionNode prev;

	private boolean mark;

	public CollisionNode(CollisionObject actor, BSPCollisionNode node) {
		this.actor = actor;
		this.node = node;
		CollisionNode first = BSPCollisionChecker.getNodeForActor(actor);
		this.next = first;
		BSPCollisionChecker.setNodeForActor(actor, this);
		if (this.next != null) {
			this.next.prev = this;
		}

		this.mark = true;
	}

	public void clearMark() {
		this.mark = false;
	}

	public void mark() {
		this.mark = true;
	}

	public boolean checkMark() {
		boolean markVal = this.mark;
		this.mark = false;
		return markVal;
	}

	public CollisionObject getActor() {
		return this.actor;
	}

	public BSPCollisionNode getBSPNode() {
		return this.node;
	}

	public CollisionNode getNext() {
		return this.next;
	}

	public void remove() {
		this.removed();
		this.node.actorRemoved(this.actor);
	}

	public void removed() {
		if (this.prev == null) {
			BSPCollisionChecker.setNodeForActor(this.actor, this.next);
		} else {
			this.prev.next = this.next;
		}
		if (this.next != null) {
			this.next.prev = this.prev;
		}
	}

	public void dispose() {
		if (node != null) {
			node = null;
		}
		if (next != null) {
			next.dispose();
			next = null;
		}
		if (prev != null) {
			prev.dispose();
			prev = null;
		}

	}
}
