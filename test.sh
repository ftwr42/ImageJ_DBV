#!/bin/zsh

echo 'Start Copy to ImageJ\nhome path: '$PWD

javaDir='/Users/ftwr/WORKSPACES/W_UNI/W_DIGIBILD/HelloWorld_DigiBild/HelloWorld_Plugin.java'
libDir='/Users/ftwr/WORKSPACES/W_UNI/W_DIGIBILD/ImageJ.app/Contents/Java/ij.jar'
javacPath='/Users/ftwr/Library/Java/JavaVirtualMachines/corretto-1.8.0_332/Contents/Home/bin/javac'
pluginDir='/Applications/ImageJ.app/plugins/My_Plugins'
ijToolsDir='/Users/ftwr/WORKSPACES/W_UNI/W_DIGIBILD/HelloWorld_DigiBild/IJTools.java'
imageToolsDir='/Users/ftwr/WORKSPACES/W_UNI/W_DIGIBILD/HelloWorld_DigiBild/ImageTools.java'
#frameDir='/Users/ftwr/WORKSPACES/W_UNI/W_DIGIBILD/HelloWorld_DigiBild/Frame.java'
sourceDir=$PWD'/out'

#rm -r -f $sourceDir
#echo 'remove: '$sourceDir
#mkdir $sourceDir
#echo 'mkdir: '$sourceDir

#komipiliere alle dateien die mit *.java enden
for i in $(ls | grep .java) ; do

  $javacPath $i $ijToolsDir $imageToolsDir -cp $libDir
  echo 'compile '$i'\n\tfrom: ' $dirFrom'/'$i'\n\tto: '$javaDir

done

for i in $(ls | grep .class) ; do

  cp -f $i $pluginDir
  rm $i
  echo 'copy '$i'\n\tfrom: ' $PWD'/'$i'\n\tto: '$pluginDir

done

cd /Applications
open ImageJ.app