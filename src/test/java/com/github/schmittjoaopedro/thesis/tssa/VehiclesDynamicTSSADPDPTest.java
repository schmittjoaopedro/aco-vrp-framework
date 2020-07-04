package com.github.schmittjoaopedro.thesis.tssa;

import com.github.schmittjoaopedro.vrp.thesis.ALNS_DPDP;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.LNSOptimizer;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.tabu_sa.TABU_SA_DPDP;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class VehiclesDynamicTSSADPDPTest {

    private static int maxIterations = 25000;

    private static final String dpdptw100Directory;

    private static final String dpdptw400Directory;

    private static final String dpdptw800Directory;

    static {
        dpdptw100Directory = Paths.get(VehiclesDynamicTSSADPDPTest.class.getClassLoader().getResource("dpdptw_100").getFile().substring(1)).toString();
        dpdptw400Directory = Paths.get(VehiclesDynamicTSSADPDPTest.class.getClassLoader().getResource("dpdptw_400").getFile().substring(1)).toString();
        dpdptw800Directory = Paths.get(VehiclesDynamicTSSADPDPTest.class.getClassLoader().getResource("dpdptw_800").getFile().substring(1)).toString();
    }

    @Test
    public void dpdptw_lc103_a_0_1_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw100Directory, "lc103_a_0.1.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.enableVehicleControlCenter();
        //solver.enablePrintOperation("C:\\Temp\\route-tracker\\print");
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.tours.size()).isEqualTo(10);
        assertThat(solutionBest.totalCost).isEqualTo(846.554652078846);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(10136.664226514264);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(290.10957443541827);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("Start time 0.25 Route = 0 25 27 29 30 28 26 103 23 22 21 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 5 3 7 8 10 11 9 6 4 2 1 75 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 67 65 62 74 72 61 64 68 66 69 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 42 41 40 44 43 45 48 51 50 52 49 47 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 90 87 86 83 82 84 85 88 89 91 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 32 33 31 35 104 37 38 39 36 34 20 24 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 13 17 18 19 15 16 14 12 0");
        assertThat(summaryResults).contains("Start time 0.25 Route = 0 98 96 95 94 92 93 102 97 100 99 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 57 55 54 53 56 58 60 59 46 101 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 81 78 76 71 70 73 77 79 80 63 0");
    }

    @Test
    public void dpdptw_lc103_a_0_5_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw100Directory, "lc103_a_0.5.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.enableVehicleControlCenter();
        //solver.enablePrintOperation("C:\\Temp\\route-tracker\\print");
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.tours.size()).isEqualTo(10);
        assertThat(solutionBest.totalCost).isEqualTo(1356.6436991127086);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(10977.823037457205);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(621.1793383444958);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 67 65 3 8 93 102 97 100 83 82 86 89 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 42 41 40 44 43 45 48 51 50 52 49 47 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 87 25 27 30 88 23 22 21 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 32 33 62 74 72 61 64 68 66 69 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 55 59 35 104 31 38 36 34 29 28 26 103 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 13 17 19 15 37 39 18 16 14 12 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 81 78 76 70 71 77 79 80 73 63 0");
        assertThat(summaryResults).contains("Start time 60.32 Route = 0 57 54 53 56 58 60 46 101 20 24 0");
        assertThat(summaryResults).contains("Start time 63.0 Route = 0 98 90 92 84 85 94 99 96 95 91 0");
        assertThat(summaryResults).contains("Start time 105.0 Route = 0 5 7 10 11 9 6 4 2 1 75 0");
    }

    @Test
    public void dpdptw_lc103_q_0_0_1_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw100Directory, "lc103_q_0_0.1.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.enableVehicleControlCenter();
        //solver.enablePrintOperation("C:\\Temp\\route-tracker\\print");
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.tours.size()).isEqualTo(16);
        assertThat(solutionBest.totalCost).isEqualTo(1554.078097092758);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(11665.210281198857);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(1111.1321841060992);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 67 65 25 27 30 23 22 21 29 28 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 13 17 19 15 14 12 94 99 96 95 26 103 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 32 33 37 35 104 31 38 39 36 34 18 16 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 81 78 76 70 77 79 80 71 73 63 0");
        assertThat(summaryResults).contains("Start time 28.0 Route = 0 87 62 74 88 61 66 83 82 0");
        assertThat(summaryResults).contains("Start time 32.0 Route = 0 55 57 53 59 0");
        assertThat(summaryResults).contains("Start time 39.0 Route = 0 42 41 40 44 48 45 72 68 50 49 46 101 0");
        assertThat(summaryResults).contains("Start time 123.35 Route = 0 5 3 8 11 0");
        assertThat(summaryResults).contains("Start time 210.0 Route = 0 54 56 58 60 64 69 43 51 0");
        assertThat(summaryResults).contains("Start time 216.0 Route = 0 98 92 93 102 97 100 0");
        assertThat(summaryResults).contains("Start time 303.0 Route = 0 10 7 9 6 4 2 85 91 0");
        assertThat(summaryResults).contains("Start time 311.0 Route = 0 90 84 0");
        assertThat(summaryResults).contains("Start time 910.0 Route = 0 86 89 0");
        assertThat(summaryResults).contains("Start time 922.3 Route = 0 52 47 0");
        assertThat(summaryResults).contains("Start time 928.0 Route = 0 1 75 0");
        assertThat(summaryResults).contains("Start time 936.0 Route = 0 20 24 0");
    }

    @Test
    public void dpdptw_lc103_q_0_0_5_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw100Directory, "lc103_q_0_0.5.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.enableVehicleControlCenter();
        //solver.enablePrintOperation("C:\\Temp\\route-tracker\\print");
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.tours.size()).isEqualTo(15);
        assertThat(solutionBest.totalCost).isEqualTo(1300.2940250316285);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(11340.450737111356);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(1040.1567120797283);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 67 65 61 62 74 66 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 5 3 7 8 10 11 9 6 4 2 1 75 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 42 41 40 44 48 45 72 68 50 49 46 101 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 32 33 35 104 0");
        assertThat(summaryResults).contains("Start time 0.25 Route = 0 57 55 54 53 56 58 60 59 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 13 17 18 19 15 16 14 12 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 81 78 76 70 77 79 80 71 73 63 0");
        assertThat(summaryResults).contains("Start time 28.0 Route = 0 87 96 95 90 84 88 83 82 86 89 0");
        assertThat(summaryResults).contains("Start time 118.0 Route = 0 25 27 29 30 28 26 103 23 22 21 20 24 0");
        assertThat(summaryResults).contains("Start time 216.0 Route = 0 98 92 93 102 97 100 0");
        assertThat(summaryResults).contains("Start time 297.0 Route = 0 31 37 38 39 36 34 0");
        assertThat(summaryResults).contains("Start time 581.0 Route = 0 64 69 43 51 0");
        assertThat(summaryResults).contains("Start time 589.0 Route = 0 94 99 0");
        assertThat(summaryResults).contains("Start time 928.48 Route = 0 85 91 0");
        assertThat(summaryResults).contains("Start time 936.39 Route = 0 52 47 0");
    }

    @Test
    public void dpdptw_lr1_4_8_q_0_0_1_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw400Directory, "LR1_4_8_q_0_0.1.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.enableVehicleControlCenter();
        //solver.enablePrintOperation("C:\\Temp\\route-tracker\\print");
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.tours.size()).isEqualTo(36);
        assertThat(solutionBest.totalCost).isEqualTo(10548.287588758778);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(16514.60778007495);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(1966.3201913161788);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 30 343 6 62 264 198 202 53 370 17 205 308 149 14 101 355 0");
        assertThat(summaryResults).contains("Start time 0.16 Route = 0 304 346 379 65 173 161 269 199 341 183 234 330 313 342 193 194 170 84 298 418 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 164 72 377 294 113 169 142 391 259 225 81 271 320 373 312 192 285 386 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 291 329 307 34 260 394 344 276 401 215 310 136 267 387 254 66 255 3 374 402 371 365 356 69 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 389 292 316 331 300 93 123 245 124 108 0");
        assertThat(summaryResults).contains("Start time 0.16 Route = 0 227 28 290 64 253 154 158 115 242 381 340 189 153 197 398 326 82 221 305 167 38 209 334 107 0");
        assertThat(summaryResults).contains("Start time 0.16 Route = 0 218 378 87 230 151 63 314 89 180 317 372 275 5 360 268 211 117 39 145 91 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 190 16 380 309 112 296 299 174 231 239 128 74 100 31 399 102 41 33 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 302 163 90 184 4 207 29 144 408 20 333 295 208 352 176 212 61 235 71 159 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 155 328 246 196 99 114 32 139 238 237 23 22 301 132 265 417 222 350 75 293 37 407 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 216 375 278 263 110 134 67 21 363 126 135 297 283 345 383 50 201 152 419 195 0");
        assertThat(summaryResults).contains("Start time 19.0 Route = 0 303 421 388 182 7 88 319 335 130 258 323 137 186 76 250 73 315 19 318 251 0");
        assertThat(summaryResults).contains("Start time 27.0 Route = 0 79 52 175 409 125 325 280 261 78 412 140 252 200 83 12 403 157 351 0");
        assertThat(summaryResults).contains("Start time 122.05 Route = 0 94 349 228 165 105 111 358 272 306 384 392 1 80 332 18 348 248 406 0");
        assertThat(summaryResults).contains("Start time 141.0 Route = 0 86 172 214 338 92 122 8 162 26 56 240 141 129 366 273 413 362 243 0");
        assertThat(summaryResults).contains("Start time 474.2 Route = 0 232 249 321 55 247 286 223 390 0");
        assertThat(summaryResults).contains("Start time 494.0 Route = 0 131 97 70 68 322 147 0");
        assertThat(summaryResults).contains("Start time 494.0 Route = 0 364 49 25 354 257 15 0");
        assertThat(summaryResults).contains("Start time 499.0 Route = 0 42 219 420 96 266 191 57 9 405 178 0");
        assertThat(summaryResults).contains("Start time 501.0 Route = 0 185 324 177 148 0");
        assertThat(summaryResults).contains("Start time 502.02 Route = 0 36 77 284 397 220 224 0");
        assertThat(summaryResults).contains("Start time 517.0 Route = 0 385 103 393 339 262 156 0");
        assertThat(summaryResults).contains("Start time 520.0 Route = 0 279 229 59 2 289 24 395 359 0");
        assertThat(summaryResults).contains("Start time 520.03 Route = 0 277 46 382 187 282 274 0");
        assertThat(summaryResults).contains("Start time 521.0 Route = 0 44 204 35 121 396 166 0");
        assertThat(summaryResults).contains("Start time 525.17 Route = 0 367 171 361 415 210 13 0");
        assertThat(summaryResults).contains("Start time 533.05 Route = 0 27 327 369 47 188 416 0");
        assertThat(summaryResults).contains("Start time 548.01 Route = 0 127 45 160 119 0");
        assertThat(summaryResults).contains("Start time 556.05 Route = 0 241 256 414 58 422 118 233 281 0");
        assertThat(summaryResults).contains("Start time 560.0 Route = 0 85 206 104 337 376 410 0");
        assertThat(summaryResults).contains("Start time 564.0 Route = 0 336 98 120 404 368 95 133 146 217 203 0");
        assertThat(summaryResults).contains("Start time 578.0 Route = 0 357 270 244 109 400 43 0");
        assertThat(summaryResults).contains("Start time 578.0 Route = 0 353 236 213 143 181 411 10 116 0");
        assertThat(summaryResults).contains("Start time 591.26 Route = 0 311 51 179 60 54 40 11 226 0");
        assertThat(summaryResults).contains("Start time 599.0 Route = 0 168 287 347 106 150 48 0");
        assertThat(summaryResults).contains("Start time 650.0 Route = 0 288 138 0");

    }

    @Test
    public void dpdptw_lc104_a_1_0_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw100Directory, "lc104_a_1.0.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.enableVehicleControlCenter();
        //solver.enablePrintOperation("C:\\Temp\\route-tracker\\print");
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isFalse(); // Isn't feasible because were required more vehicles than the problem has available
        assertThat(solutionBest.tours.size()).isEqualTo(30);
        assertThat(solutionBest.totalCost).isEqualTo(2388.366488373299);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(12039.885960390293);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(651.5194720169947);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 67 55 53 54 58 60 59 68 80 63 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 90 87 76 82 84 77 79 73 78 70 47 105 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 13 16 19 15 11 97 100 6 12 10 0");
        assertThat(summaryResults).contains("Start time 28.0 Route = 0 20 25 62 44 48 69 72 64 0");
        assertThat(summaryResults).contains("Start time 123.0 Route = 0 3 8 92 104 5 9 94 99 95 93 0");
        assertThat(summaryResults).contains("Start time 212.0 Route = 0 65 74 0");
        assertThat(summaryResults).contains("Start time 306.28 Route = 0 31 38 0");
        assertThat(summaryResults).contains("Start time 574.0 Route = 0 28 23 0");
        assertThat(summaryResults).contains("Start time 583.0 Route = 0 7 4 0");
        assertThat(summaryResults).contains("Start time 876.0 Route = 0 57 56 0");
        assertThat(summaryResults).contains("Start time 878.3 Route = 0 35 39 0");
        assertThat(summaryResults).contains("Start time 886.0 Route = 0 32 33 103 37 0");
        assertThat(summaryResults).contains("Start time 889.43 Route = 0 81 71 0");
        assertThat(summaryResults).contains("Start time 892.0 Route = 0 98 96 0");
        assertThat(summaryResults).contains("Start time 893.0 Route = 0 17 18 0");
        assertThat(summaryResults).contains("Start time 894.0 Route = 0 36 34 0");
        assertThat(summaryResults).contains("Start time 898.0 Route = 0 83 88 0");
        assertThat(summaryResults).contains("Start time 905.0 Route = 0 85 89 0");
        assertThat(summaryResults).contains("Start time 908.0 Route = 0 86 91 0");
        assertThat(summaryResults).contains("Start time 915.38 Route = 0 45 51 0");
        assertThat(summaryResults).contains("Start time 916.0 Route = 0 42 52 0");
        assertThat(summaryResults).contains("Start time 918.0 Route = 0 40 46 0");
        assertThat(summaryResults).contains("Start time 919.0 Route = 0 50 49 0");
        assertThat(summaryResults).contains("Start time 920.0 Route = 0 61 66 0");
        assertThat(summaryResults).contains("Start time 923.0 Route = 0 30 21 0");
        assertThat(summaryResults).contains("Start time 924.0 Route = 0 2 1 75 106 0");
        assertThat(summaryResults).contains("Start time 925.0 Route = 0 27 29 24 101 0");
        assertThat(summaryResults).contains("Start time 926.0 Route = 0 43 41 0");
        assertThat(summaryResults).contains("Start time 932.0 Route = 0 26 22 0");
        assertThat(summaryResults).contains("Start time 976.0 Route = 0 14 102 0");
    }

    @Test
    public void dpdptw_lrc2_8_7_q_0_1_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw800Directory, "LRC2_8_7_q_0_0.1.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.enableVehicleControlCenter();
        //solver.enablePrintOperation("C:\\Temp\\route-tracker\\print");
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue(); // Isn't feasible because were required more vehicles than the problem has available
        assertThat(solutionBest.tours.size()).isEqualTo(40);
        assertThat(solutionBest.totalCost).isEqualTo(42967.4587706411);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(134854.441187772);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(83886.98241713086);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("Start time 1.26 Route = 0 640 543 385 161 675 454 345 470 325 791 270 554 607 743 228 694 707 408 730 768 765 397 302 267 414 113 215 263 676 357 630 148 112 802 502 517 33 786 384 126 172 11 336 734 381 787 0");
        assertThat(summaryResults).contains("Start time 1.26 Route = 0 398 701 244 487 50 527 490 715 324 539 383 757 157 192 434 66 253 753 185 740 794 501 556 418 629 158 624 186 92 429 602 339 609 464 180 286 0");
        assertThat(summaryResults).contains("Start time 1.26 Route = 0 320 391 229 162 503 744 314 632 54 572 177 86 716 400 497 275 260 739 16 788 31 626 526 168 595 124 404 262 34 292 566 131 741 255 500 210 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 288 521 394 420 704 114 358 309 793 303 703 190 559 506 686 460 425 445 606 520 508 23 551 87 617 88 596 318 642 415 44 714 0");
        assertThat(summaryResults).contains("Start time 1.26 Route = 0 616 439 36 32 399 547 35 343 478 577 375 777 672 749 587 622 100 174 47 307 648 257 770 12 677 181 1 659 574 284 176 143 217 8 568 477 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 645 45 494 421 306 223 310 733 661 756 681 634 117 498 68 115 738 279 405 137 687 541 108 396 216 615 48 230 359 773 0");
        assertThat(summaryResults).contains("Start time 1.26 Route = 0 401 728 354 67 509 717 264 121 505 426 724 355 662 779 291 170 747 693 107 20 647 173 455 612 328 277 372 200 348 696 748 535 0");
        assertThat(summaryResults).contains("Start time 102.0 Route = 0 254 772 356 93 99 76 319 301 360 582 105 736 548 513 441 752 132 69 759 95 515 537 558 540 125 363 21 207 599 90 382 493 371 53 226 468 390 63 0");
        assertThat(summaryResults).contains("Start time 115.0 Route = 0 528 789 308 338 0");
        assertThat(summaryResults).contains("Start time 164.0 Route = 0 191 631 683 147 165 203 553 473 387 557 340 689 538 97 198 723 274 569 605 245 657 169 0");
        assertThat(summaryResults).contains("Start time 182.0 Route = 0 73 175 737 652 764 368 495 379 156 450 149 671 142 94 641 446 650 727 10 598 463 644 135 15 0");
        assertThat(summaryResults).contains("Start time 294.0 Route = 0 3 437 52 247 346 690 660 573 70 120 655 763 610 542 110 428 637 524 317 499 251 760 283 578 643 801 322 376 313 654 259 658 83 462 167 709 0");
        assertThat(summaryResults).contains("Start time 326.0 Route = 0 778 443 758 332 600 30 9 211 575 565 646 511 576 732 152 351 680 65 452 564 623 783 726 123 579 699 269 365 0");
        assertThat(summaryResults).contains("Start time 340.0 Route = 0 316 412 725 674 122 141 341 471 685 218 604 389 187 438 243 208 504 585 449 51 166 183 781 620 342 719 225 361 280 111 0");
        assertThat(summaryResults).contains("Start time 379.0 Route = 0 432 534 755 484 281 496 492 466 6 4 702 485 561 234 297 670 603 555 81 369 304 91 0");
        assertThat(summaryResults).contains("Start time 400.0 Route = 0 562 766 130 708 491 334 140 248 666 525 238 13 46 362 530 261 792 299 0");
        assertThat(summaryResults).contains("Start time 431.43 Route = 0 523 480 7 710 222 138 440 129 60 546 159 649 104 231 61 560 448 155 775 795 296 782 378 531 0");
        assertThat(summaryResults).contains("Start time 469.0 Route = 0 784 436 145 692 29 89 103 214 796 798 453 588 608 651 59 447 101 374 184 722 700 347 182 684 289 109 164 665 785 139 188 451 293 153 377 682 43 417 456 118 364 457 0");
        assertThat(summaryResults).contains("Start time 472.0 Route = 0 713 774 55 393 458 435 409 258 220 589 403 119 580 285 77 488 0");
        assertThat(summaryResults).contains("Start time 491.0 Route = 0 209 549 481 790 58 197 256 691 613 179 344 419 628 518 212 529 78 295 581 206 406 619 329 273 413 18 679 202 536 510 154 673 213 146 0");
        assertThat(summaryResults).contains("Start time 516.0 Route = 0 163 395 42 2 532 411 522 271 272 431 235 268 315 586 39 134 204 150 0");
        assertThat(summaryResults).contains("Start time 517.0 Route = 0 349 366 635 516 410 287 584 668 352 106 512 656 219 326 27 282 127 465 189 601 195 594 688 196 593 136 25 721 205 422 489 442 0");
        assertThat(summaryResults).contains("Start time 605.0 Route = 0 236 507 697 133 669 323 62 712 483 780 0");
        assertThat(summaryResults).contains("Start time 619.0 Route = 0 331 591 751 102 237 312 57 160 767 639 711 729 250 678 486 392 0");
        assertThat(summaryResults).contains("Start time 642.74 Route = 0 746 614 227 330 240 300 38 571 583 430 618 735 242 370 41 49 80 246 98 79 427 85 636 769 241 638 444 380 0");
        assertThat(summaryResults).contains("Start time 672.0 Route = 0 720 373 37 75 590 14 96 26 519 350 592 533 800 233 621 56 698 19 664 706 771 461 84 367 0");
        assertThat(summaryResults).contains("Start time 756.0 Route = 0 472 337 386 459 353 249 335 305 0");
        assertThat(summaryResults).contains("Start time 764.74 Route = 0 327 407 311 72 433 28 294 475 550 221 469 552 0");
        assertThat(summaryResults).contains("Start time 769.0 Route = 0 416 563 544 252 705 718 545 479 151 762 265 128 482 653 474 797 5 22 0");
        assertThat(summaryResults).contains("Start time 840.0 Route = 0 742 201 424 776 0");
        assertThat(summaryResults).contains("Start time 869.0 Route = 0 71 754 633 224 17 232 695 402 333 597 116 761 321 276 0");
        assertThat(summaryResults).contains("Start time 1718.0 Route = 0 194 74 0");
        assertThat(summaryResults).contains("Start time 1823.0 Route = 0 476 290 514 171 750 611 144 667 178 423 266 799 298 24 0");
        assertThat(summaryResults).contains("Start time 2082.0 Route = 0 570 278 0");
        assertThat(summaryResults).contains("Start time 2518.0 Route = 0 663 40 467 64 0");
        assertThat(summaryResults).contains("Start time 2614.0 Route = 0 567 388 0");
        assertThat(summaryResults).contains("Start time 3633.0 Route = 0 731 239 0");
        assertThat(summaryResults).contains("Start time 3655.0 Route = 0 627 625 0");
        assertThat(summaryResults).contains("Start time 4663.0 Route = 0 745 82 0");
        assertThat(summaryResults).contains("Start time 4920.0 Route = 0 199 193 0");
    }
}
