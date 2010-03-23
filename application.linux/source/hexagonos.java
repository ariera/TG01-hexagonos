import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.*; 
import java.awt.image.*; 
import java.awt.event.*; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class hexagonos extends PApplet {

PImage img;
PImage output;
boolean lineaSiLineaNo = false;

public void setup() {
  size(256, 356);
  img = loadImage("lena.jpg");
  output = createImage(img.width, img.height, RGB);
}

public void draw() {
  img.loadPixels();
  output.loadPixels();
  hexagonizarImagen(img, output, lineaSiLineaNo);
  output.updatePixels();
  image(output,0,0);

}



public void mousePressed(){
  if (mouseButton == LEFT) 
    dibujarPixel(img.height+5, hexagonalPixel(mouseX,mouseY,img), promedioHexagono(mouseX,mouseY,img));
  else {
    lineaSiLineaNo = !lineaSiLineaNo;    
    // Your attention please, guarrada de alto nivel incoming!!
    // esto sirve para "limpiar el lienzo" antes de redibujar sobre \u00e9l
    // como no hay primitiva de "
    output = createImage(img.width, img.height, RGB); 
  }
}




// think of an Hexagonal pixel with a shape like this:
// ##
//####
// ##
// Number the pixels like this:
// 01
//2345
// 67
public int[] hexagonalPixel(int x, int y, PImage img){
  int[] loc = {
    x + y*img.width,         //pixel 0
    (x+1) + (y+0)*img.width, //pixel 1
    (x-1) + (y+1)*img.width, //pixel 2
    (x+0) + (y+1)*img.width, //pixel 3
    (x+1) + (y+1)*img.width, //pixel 4
    (x+2) + (y+1)*img.width, //pixel 5
    (x+0) + (y+2)*img.width, //pixel 6
    (x+1) + (y+2)*img.width  //pixel 7
  };
  return loc;
}

public void hexagonizarImagen(PImage src, PImage dest, boolean lineaSiLineaNo){
  int initX = 0;
  for (int y = 0; y < src.height-2; y+=2) {
    if (!lineaSiLineaNo) 
      initX = (y % 4 == 0) ? 0 : 2;

    for (int x = initX; x < src.width; x+=4) {
      pintarHexagono(x,y,src,dest);
    }
  }
}

public void pintarHexagono(int x, int y, PImage src, PImage dest){
  int[] pixHex = hexagonalPixel(x,y,src);
  int promedio = promedioHexagono(x,y,src);
  for (int i = 0; i < pixHex.length; i++) {
    dest.pixels[pixHex[i]] = promedio;
  }
}

public int promedioHexagono(int x, int y, PImage img){
  int[] loc = hexagonalPixel(x,y,img);

  int rojos = 0;
  int verdes = 0;
  int azules = 0;

  img.loadPixels(); 
  for (int i = 0; i < loc.length; i++) {
    rojos += red(img.pixels[loc[i]]);
    verdes += green(img.pixels[loc[i]]);
    azules += blue(img.pixels[loc[i]]);
  }

  return color(rojos/8, verdes/8, azules/8);
}

// Altura define a partir de que punto se dibujaran el pixel y el cuadrado resultante del promedio
// colors es un array con los colores de los 8 pixeles q componen el pixel hexagonal+
// promedio es el promedio de color de los 8 pixeles
public void dibujarPixel(int altura, int[] loc, int promedio){
  int x = 10;
  int y = altura;
  int w = 20;
  int[] colors = {
    0,0,0,0,0,0,0,0          };

  for (int i = 0; i < loc.length; i++) {
    colors[i] = img.pixels[loc[i]];
  }

  fill(colors[0]);
  rect(x+(w*1), y+(w*0), w, w);
  fill(colors[1]);
  rect(x+(w*2), y+(w*0), w, w);
  fill(colors[2]);
  rect(x+(w*0), y+(w*1), w, w);
  fill(colors[3]);
  rect(x+(w*1), y+(w*1), w, w);
  fill(colors[4]);
  rect(x+(w*2), y+(w*1), w, w);
  fill(colors[5]);
  rect(x+(w*3), y+(w*1), w, w);
  fill(colors[6]);
  rect(x+(w*1), y+(w*2), w, w);
  fill(colors[7]);
  rect(x+(w*2), y+(w*2), w, w);

  fill(promedio);
  rect(x+100, y, 60,60);


}

















  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "hexagonos" });
  }
}
