tempPath <- "C:/Temp/ADTSP/"
pathName = "C:/Temp/ADTSP/ALL_RESULTS_COMPILED.csv"
dataSep = ";"
header <- read.csv(pathName, sep = dataSep, nrows = 1)
data = read.csv(pathName, sep = dataSep, header = T, colClasses = paste(rep("character", ncol(header))))
probType <- "ADTSP"

normalizeName <- function (algName) {
  g <- regexpr("\\_[^\\_]*$", algName)
  substr(algName, 0, g - 1)
}


readFile <- function(fileName, data, idx) {
  conn <- file(fileName,open="r")
  linn <-readLines(conn)
  close(conn)
  values <- c()
  for (i in 1:length(linn)){
    temp <- regexpr("DIV:*", linn[i])
    value <- substr(linn[i], temp[1] + attr(temp, "match.length"), nchar(linn[i]))
    value <- as.double(value)
    data[8 + i,idx] <- value
    values <- c(values, value)
  }
  data[7, idx] <- mean(values)
  data[8, idx] <- sd(values)
  data
}

for (idx in 2:ncol(data)) {
  algName <- normalizeName(names(data)[idx])
  instName <- data[1,idx]
  freqName <- data[2,idx]
  magName <- data[3,idx]
  rhoName <- data[4,idx]
  alphaName <- data[5,idx]
  betaName <- data[6,idx]
  fileName <- paste(instName, "_",
                    "freq_", freqName, "_",
                    "mag_", magName, "_",
                    "rho_", rhoName, "_",
                    "alpha_", format(round(as.numeric(alphaName), 1), nsmall = 1), "_",
                    "beta_", format(round(as.numeric(betaName), 1), nsmall = 1), "_",
                    algName, "_", probType, ".txt", sep ="")
  data <- readFile(paste(tempPath, fileName, sep = ""), data, idx)
}

write.csv(data, file = paste(tempPath, "diversity.csv", sep = ""), quote = F, row.names = F)
