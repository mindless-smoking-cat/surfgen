package generators;

/*
Copyright (c) 2009, Nerius Landys
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer. 
2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution. 
3. The name of the author may not be used to endorse or promote products
derived from this software without specific prior written permission. 

THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * @version $Revision: 1.5 $
 */

// changed EXECUTION_TIME_MILLIS from 4000 to 10000, to prevent premature shutdowns

public final class Shutdown implements Runnable {

    private final static int EXECUTION_TIME_MILLIS = 12000; // changed from 4000
    private final static int KILL_EXIT_CODE = 2;
    public static boolean ShutDownOK = true;

    public static void soon() {
        ShutDownOK = true;
        Runnable shutdownRunner = new Shutdown();
        Thread shutdownThread = new Thread(shutdownRunner);
        shutdownThread.setDaemon(true);
        shutdownThread.setPriority(Thread.MAX_PRIORITY);
        shutdownThread.start();
    }

    static void abort() {
        ShutDownOK = false;
    }

    public final void run() {
        final long endTime = System.currentTimeMillis() + EXECUTION_TIME_MILLIS;
        while (true) {
            final long timeNow = System.currentTimeMillis();
            if (timeNow >= endTime) {
                break;
            }
            try {
                Thread.sleep(endTime - timeNow);
            } catch (InterruptedException exc) {
            }
        }
        if (ShutDownOK) {
            // System.exit(KILL_EXIT_CODE);
            return;
        }
    }

}