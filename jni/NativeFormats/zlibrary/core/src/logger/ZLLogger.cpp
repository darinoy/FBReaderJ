/*
 * Copyright (C) 2009-2012 Geometer Plus <contact@geometerplus.com>
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

#include <AndroidUtil.h>

#include "ZLLogger.h"

const std::string ZLLogger::DEFAULT_CLASS;

ZLLogger *ZLLogger::ourInstance = 0;

static void printInternal(const std::string &message) {
	JNIEnv *env = AndroidUtil::getEnv();
	jstring javaMessage = AndroidUtil::createJavaString(env, message);
	env->CallVoidMethod(
		AndroidUtil::OBJECT_java_lang_System_err,
		AndroidUtil::MID_java_io_PrintStream_println,
		javaMessage
	);
	env->DeleteLocalRef(javaMessage);
}

ZLLogger &ZLLogger::Instance() {
	if (ourInstance == 0) {
		ourInstance = new ZLLogger();
	}
	return *ourInstance;
}

ZLLogger::ZLLogger() {
	myEnv = AndroidUtil::getEnv();
	mySystemErr = 0;
	myPrintStreamClass = 0;
}

ZLLogger::~ZLLogger() {
	myEnv->DeleteLocalRef(mySystemErr);
	myEnv->DeleteLocalRef(myPrintStreamClass);
}

void ZLLogger::registerClass(const std::string &className) {
	myRegisteredClasses.insert(className);
}

void ZLLogger::print(const std::string &className, const std::string &message) const {
	if (className == DEFAULT_CLASS) {
		printInternal(message);
	} else {
		std::set<std::string>::const_iterator it =
			myRegisteredClasses.find(className);

		if (it != myRegisteredClasses.end()) {
			printInternal(className + ": " + message);
		}
	}
}

void ZLLogger::println(const std::string &className, const std::string &message) const {
	print(className, message);
}
