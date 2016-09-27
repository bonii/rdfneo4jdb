/**
 *
 *  Copyright (C) 2016 Vivek Shah 

 *  This file is part of RDFNeo4JDB

 *  RDFNeo4JDB is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.

 *  RDFNeo4JDB is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software Foundation,
 *  Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA

 *  Author Vivek Shah <bonii at kernelspace.in>
 *  GraphDBException.java created on Sep 27, 2016
 *
**/
package main.java.interfaces;

public class GraphDBException extends Exception {

	private static final long serialVersionUID = 3492431818988443283L;

	public GraphDBException() {

	}

	public GraphDBException(String arg0) {
		super(arg0);
	}

	public GraphDBException(Throwable arg0) {
		super(arg0);
	}

	public GraphDBException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public GraphDBException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
