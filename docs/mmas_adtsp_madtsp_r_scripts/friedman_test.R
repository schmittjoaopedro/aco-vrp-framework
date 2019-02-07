pathName = "C:/Temp/ADTSPM/ALL_RESULTS_COMPILED.csv"
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
  log <- paste("Friedman test for instance =", instance, ", freq =", freq,", mag =",mag)
  ftest <- friedman.test(temp)$p.value
  log <- paste(log, "p-value =", round(ftest, digits = 5),"\n")
  for (i in 1:ncol(temp)) {
    for (j in i:ncol(temp)) {
      if (i != j) {
        wtest <- wilcox.test(temp[,i], temp[,j])
        log <- paste(log, "    Wilcoxon:", colnames(temp)[i], "<->", colnames(temp)[j], 
                     "p-value =", round(wtest$p.value, digits = 5), "\n")
      }
    }
  }
  cat(log)
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