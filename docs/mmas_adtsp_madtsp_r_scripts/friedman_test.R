pathName = "C:/Temp/ADTSP/ALL_RESULTS_COMPILED.csv"
dataSep = ","
header <- read.csv(pathName, sep = dataSep, nrows = 1)
data = read.csv(pathName, sep = dataSep, header = T, colClasses = paste(rep("character", ncol(header))))
probType <- "MADTSP"

normalizeName <- function (algName) {
  g <- regexpr("\\_[^\\_]*$", algName)
  substr(algName, 0, g - 1)
}


executeStatisticalTest <- function(data, instance, freq, mag) {
  temp <- data
  temp <- temp[,temp[1,]==instance]
  temp <- temp[,temp[2,]==freq]
  temp <- temp[,temp[3,]==mag]
  names(temp) = normalizeName(names(temp))
  temp <- temp[9:nrow(temp),]
  rownames(temp) <- 1:1000
  temp <- data.frame(sapply(temp, function(x) as.numeric(as.character(x))))
  temp <- as.matrix(temp)
  # Friedman
  log <- paste("Friedman test for instance =", instance, ", freq =", freq,", mag =",mag)
  ftest <- friedman.test(temp)$p.value
  log <- paste(log, "p-value =", round(ftest, digits = 5),"\n")
  # Wilcoxn
  for (i in 1:ncol(temp)) {
    for (j in i:ncol(temp)) {
      if (i != j) {
        wtest <- wilcox.test(temp[,i], temp[,j])
        log <- paste(log, "    Wilcoxon:", colnames(temp)[i], "<->", colnames(temp)[j], 
                     "p-value =", round(wtest$p.value, digits = 5), "\n")
      }
    }
  }
  # Kruskal-Wallis
  x <- c(temp[,1], temp[,2], temp[,3], temp[,4], temp[,5])
  g <- factor(rep(1:5, c(1000, 1000, 1000, 1000, 1000)), labels = colnames(temp))
  ktest <- kruskal.test(x, g)
  log <- paste(log, "Kruskal-Wallis =", round(ktest$p.value, digits = 5), "\n")
  log <- paste(log, "Mann-Whitney test with Bonferroni correction\n")
  mwtest <- pairwise.wilcox.test(x, g, p.adj = "bonf")
  cat(log)
  print(mwtest)
}

temp <- executeStatisticalTest(data, "KroA100.tsp", "10", "0.1")
temp <- executeStatisticalTest(data, "KroA100.tsp", "10", "0.5")
temp <- executeStatisticalTest(data, "KroA100.tsp", "10", "0.75")
temp <- executeStatisticalTest(data, "KroA100.tsp", "100", "0.1")
temp <- executeStatisticalTest(data, "KroA100.tsp", "100", "0.5")
temp <- executeStatisticalTest(data, "KroA100.tsp", "100", "0.75")
temp <- executeStatisticalTest(data, "KroA150.tsp", "10", "0.1")
temp <- executeStatisticalTest(data, "KroA150.tsp", "10", "0.5")
temp <- executeStatisticalTest(data, "KroA150.tsp", "10", "0.75")
temp <- executeStatisticalTest(data, "KroA150.tsp", "100", "0.1")
temp <- executeStatisticalTest(data, "KroA150.tsp", "100", "0.5")
temp <- executeStatisticalTest(data, "KroA150.tsp", "100", "0.75")
temp <- executeStatisticalTest(data, "KroA200.tsp", "10", "0.1")
temp <- executeStatisticalTest(data, "KroA200.tsp", "10", "0.5")
temp <- executeStatisticalTest(data, "KroA200.tsp", "10", "0.75")
temp <- executeStatisticalTest(data, "KroA200.tsp", "100", "0.1")
temp <- executeStatisticalTest(data, "KroA200.tsp", "100", "0.5")
temp <- executeStatisticalTest(data, "KroA200.tsp", "100", "0.75")