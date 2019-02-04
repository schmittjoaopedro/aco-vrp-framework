#pathName = "C:/Temp/ALL_RESULTS_COMPILED.csv"
pathName = "C:/Temp/diversity.csv"
type = "MADTSP"
costType = "Diversity"
dataSep = ","
header <- read.csv(pathName, sep = dataSep, nrows = 1)
data = read.csv(pathName, sep = dataSep, header = T, colClasses = paste(rep("character", ncol(header))))

normalizeName <- function (algName) {
  g <- regexpr("\\_[^\\_]*$", algName)
  substr(algName, 0, g - 1)
}

getFormattedData <- function(data, instance, freq, mag) {
  dataFrame = as.data.frame(1:1000)
  names(dataFrame) <- c("Iteration")
  for (idx in 1:length(data[1,])) {
    if (data[1, idx] == instance && data[2, idx] == freq && data[3, idx] == mag) {
       dataFrameTemp = as.data.frame(x = as.numeric(data[9:nrow(data), idx]))
       names(dataFrameTemp) <- c(normalizeName(names(data)[idx]))
       dataFrame <- cbind(dataFrame, dataFrameTemp)
    }
  }
  return(dataFrame)
}

plotData <- function(data, instance, freq, mag, type, costType) {
  formatedData <- getFormattedData(data, instance, freq, mag)
  fSize = 1.5
  title = paste(type, instance, " Freq:", freq, " Mag:", mag)
  
  minVal <- min(formatedData[,2])
  maxVal <- max(formatedData[,2])
  for (idx in 3:ncol(formatedData)) {
    if (min(formatedData[,idx]) < minVal) {
      minVal <- min(formatedData[,idx])
    }
    if (max(formatedData[,idx]) > maxVal) {
      maxVal <- max(formatedData[,idx])
    }
  }
  print(minVal)
  
  plot(formatedData$Iteration, 
       formatedData$MMAS_MEM_US, 
       type = "l", 
       col = "grey", 
       ylim = c(minVal, maxVal),
       main = title, 
       xlab = "Iteration", 
       ylab = costType, 
       cex.lab=fSize, 
       cex.axis=fSize, 
       cex.main=fSize, 
       lwd = 2)
  
  lines(formatedData$Iteration, formatedData$MMAS_MEM, col = "blue", lty=10, lwd=2)
  lines(formatedData$Iteration, formatedData$MMAS_US, col = "green", lty=10, lwd=2)
  lines(formatedData$Iteration, formatedData$MMAS_3OPT, col = "red", lty=10, lwd=2)
  lines(formatedData$Iteration, formatedData$MMAS, col = "black", lty=10, lwd=2)

  legend("topleft", 
         legend = c("MMAS_MEM_US", "MMAS_MEM", "MMAS_US", "MMAS_3OPT", "MMAS"),
         col = c("grey", "blue", "green", "red", "black"),
         cex = fSize,
         lty=c(1, 10), 
         lwd=c(2,2,2,2,2))
}

par(mar=c(3,3,2,1))
par(mfrow = c(2,3))
par(mgp=c(2, 1, 0))
plotData(data, "KroA150.tsp", "10", "0.1", type, costType)
plotData(data, "KroA150.tsp", "10", "0.5", type, costType)
plotData(data, "KroA150.tsp", "10", "0.75", type, costType)
plotData(data, "KroA150.tsp", "100", "0.1", type, costType)
plotData(data, "KroA150.tsp", "100", "0.5", type, costType)
plotData(data, "KroA150.tsp", "100", "0.75", type, costType)

