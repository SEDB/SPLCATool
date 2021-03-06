/*******************************************************************************
 * SAT4J: a SATisfiability library for Java Copyright (C) 2004-2008 Daniel Le Berre
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU Lesser General Public License Version 2.1 or later (the
 * "LGPL"), in which case the provisions of the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of the LGPL, and not to allow others to use your version of
 * this file under the terms of the EPL, indicate your decision by deleting
 * the provisions above and replace them with the notice and other provisions
 * required by the LGPL. If you do not delete the provisions above, a recipient
 * may use your version of this file under the terms of the EPL or the LGPL.
 * 
 * Based on the original MiniSat specification from:
 * 
 * An extensible SAT solver. Niklas Een and Niklas Sorensson. Proceedings of the
 * Sixth International Conference on Theory and Applications of Satisfiability
 * Testing, LNCS 2919, pp 502-518, 2003.
 *
 * See www.minisat.se for the original solver in C++.
 * 
 *******************************************************************************/

package org.sat4j.minisat;

import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;

import org.sat4j.reader.InstanceReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

/**
 * @author leberre
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public abstract class AbstractAcceptanceTestCase<T extends ISolver> extends
		TestCase {

	/**
     * 
     */
	public AbstractAcceptanceTestCase() {
		super();
	}

	/**
	 * @param arg0
	 */
	public AbstractAcceptanceTestCase(String arg0) {
		super(arg0);
	}

	protected T solver;

	protected Reader reader;

	protected abstract T createSolver();

	/**
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() {
		solver = createSolver();
		reader = createInstanceReader(solver);
	}

	protected Reader createInstanceReader(T aSolver) {
		return new InstanceReader(aSolver);
	}

	@Override
	protected void tearDown() {
		solver.reset();
	}

	protected boolean solveInstance(String filename)
			throws FileNotFoundException, ParseFormatException, IOException {
		try {
			reader.parseInstance(filename);
			solver.setTimeout(180); // set timeout to 3 minutes.
			return solver.isSatisfiable();
		} catch (ContradictionException ce) {
			return false;
		} catch (TimeoutException ce) {
			fail("Timeout: need more time to complete!");
			return false;
		}
	}

}
