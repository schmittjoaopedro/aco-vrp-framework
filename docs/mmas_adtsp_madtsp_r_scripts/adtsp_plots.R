#pathName = "C:/Temp/ALL_RESULTS_COMPILED.csv"
pathName = "C:/Temp/Diversity.csv"
header <- read.csv(pathName, sep = ";", nrows = 1)
data = read.csv(pathName, sep = ";", header = T, colClasses = paste(rep("character", ncol(header))))

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

plotData <- function(data, instance, freq, mag, type) {
  formatedData <- getFormattedData(data, instance, freq, mag)
  fSize = 1.5
  title = paste(type, instance, " Freq:", freq, " Mag:", mag)
  lwds = c(2, 2, 2, 2, 2)
  cols = c("black", "red", "green", "blue", "gray")
  
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
       formatedData[,2], 
       type = "l", 
       col = cols[1], 
       ylim = c(minVal, maxVal),
       main = title, 
       xlab = "Iteration", 
       ylab = "Fitness", 
       cex.lab=fSize, 
       cex.axis=fSize, 
       cex.main=fSize, 
       lwd = lwds[1])
  for (idx in 3:ncol(formatedData)) {
    lines(formatedData$Iteration, 
          formatedData[,idx], 
          col = cols[idx-1], 
          lty=10, 
          lwd=lwds[idx-1])
  }
  legend("topleft", 
         legend = names(formatedData)[2:ncol(formatedData)],
         col = cols,
         cex = fSize,
         lty=c(1, 10), 
         lwd=lwds)
}

par(mar=c(3,3,2,1))
par(mfrow = c(2,3))
par(mgp=c(2, 1, 0))
plotData(data, "KroA150.tsp", "10", "0.1", "MADTSP")
plotData(data, "KroA150.tsp", "10", "0.5", "MADTSP")
plotData(data, "KroA150.tsp", "10", "0.75", "MADTSP")
plotData(data, "KroA150.tsp", "100", "0.1", "MADTSP")
plotData(data, "KroA150.tsp", "100", "0.5", "MADTSP")
plotData(data, "KroA150.tsp", "100", "0.75", "MADTSP")

