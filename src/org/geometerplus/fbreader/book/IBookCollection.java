/*
 * Copyright (C) 2007-2013 Geometer Plus <contact@geometerplus.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.fbreader.book;

import java.util.List;

import org.geometerplus.zlibrary.core.filesystem.ZLFile;

import org.geometerplus.zlibrary.text.view.ZLTextPosition;

public interface IBookCollection {
	public interface Listener {
		public enum BookEvent {
			Added,
			Updated,
			Removed
		}

		public enum BuildEvent {
			Started,
			NotStarted,
			Succeeded,
			Failed,
			Completed
		}

		void onBookEvent(BookEvent event, Book book);
		void onBuildEvent(BuildEvent event);
	}

	public void addListener(Listener listener);
	public void removeListener(Listener listener);

	int size();
	List<Book> books();
	List<Book> books(String pattern);
	List<Book> recentBooks();
	List<Book> favorites();
	Book getBookByFile(ZLFile file);
	Book getBookById(long id);
	Book getRecentBook(int index);

	List<Author> authors();
	List<Tag> tags();
	List<String> series();

	boolean saveBook(Book book, boolean force);
	void removeBook(Book book, boolean deleteFromDisk);

	void addBookToRecentList(Book book);

	boolean hasFavorites();
	boolean isFavorite(Book book);
	void setBookFavorite(Book book, boolean favorite);

	ZLTextPosition getStoredPosition(long bookId);
	void storePosition(long bookId, ZLTextPosition position);

	boolean isHyperlinkVisited(Book book, String linkId);
	void markHyperlinkAsVisited(Book book, String linkId);

	List<Bookmark> allBookmarks();
	List<Bookmark> invisibleBookmarks(Book book);
	void saveBookmark(Bookmark bookmark);
	void deleteBookmark(Bookmark bookmark);
}
