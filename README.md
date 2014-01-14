ShaderCL
========

A Java library to accelerate your every day image processing by using the GPU.

```
/***************************************************************************************************/
/***************************************************************************************************/
/*                                                                                                 */
/*    SSSSSS   HH                              DD                              CCCCC    LL         */
/*   SSSSSSSS  HH                              DD                             CC   CC   LL         */
/*   SS    SS  HH                              DD                            CC     CC  LL         */
/*   SS        HH                              DD                            CC     CC  LL         */
/*    SSSS     HH HHHH       AAAAA       DDDDD DD     EEEEEEEE   RR RRRRRR   CC         LL         */
/*      SSSS   HHHHHHHHH   AAA   AAA    DDD   DDD    EE      EE  RRRR   RRR  CC         LL         */
/*         SS  HH     HH  AA      AA   DD      DD   EEEEEEEEEEE  RRR         CC     CC  LL         */
/*   SS    SS  HH     HH  AA      AA   DD      DD   EE           RR          CC     CC  LL         */
/*   SSSSSSSS  HH     HH   AA    AAAA   DD    DDDD   EE      EE  RR           CC   CC   LL         */
/*    SSSSSS   HH     HH    AAAAAA  AA   DDDDDD  DD   EEEEEEEE   RR            CCCCC    LLLLLLLL   */
/*                                                                                                 */
/*  By ARIEL WEXLER (arikwex@mit.edu)                                                              */
/*  January 12, 2014                                                                               */
/***************************************************************************************************/
/***************************************************************************************************/
```



TABLE OF CONTENTS
=================
```
1. Introduction
	a. What is it?
	b. Why is it?
	c. Applications & Speed
2. Setup \ Installation
3. Java Protocol
	a. Core Operation
	b. Parameter Passing
4. Shader Utilization
	a. Inputs and Outputs
	b. Built-in functions
5. Possible Errors
	a. Shaders Fail to Compile
	b. GPU Crash
```

[1] INTRODUCTION
================

Welcome to the ShaderCL documentation!  This readme file aims
to explain why ShaderCL is useful and how you should use it.

(a) What is it?
---------------

ShaderCL stands for Shader Computation Library.  ShaderCL is a
software library powered by OpenGL that simplifies the coding and
accelerates the execution speed of per-pixel operations on images.
In the context of a Gaussian Blur Kernal for example, a quadruple
nested "for" loop would be used to compute the result for a single
pixel.  Running such an operation on the CPU is incredibly slow.
Considering that each pixel's output is independent of the result
pixels around it, this problem is notably "embarrassingly parallel."
It is for this reason that ShaderCL exists - to help you write your
per-pixel operations and not worry about all the parallelization
issues.  The typical input is an image and the typical output is
a filtered image.  Check out the applications section for other
utility ideas.

For now, this library is only intended to support Java.  Luckily for
you, I have open-sourced the code and you are welcome to port all
of the OpenGL calls to your language of choice.  Contributions to
this cause are welcome!

(b) Why is it?
--------------

Good question.  Well, as a hobbyist game developer I decided to
start venturing in to the world of OpenGL.  While learning about
pixel shaders, I really found value in the fact that EACH pixel
of the rendered scene could run some sort of shader program.
Quickly extending that idea to the field of image processing, I
realized that I could just treat shaders as "per-pixel" operators.
LOTS of image processing kernels are characterized well by this
need for per-pixel operations, so this all seemed pretty useful.

Furthermore, I wasn't a big fan of OpenCV... Yea, I said it...
I didn't like the difficulty of setup on some machines.  I didn't
like the run speed without compiling from source for your GPU.
I didn't like the lack of control I had over pre-made functions.
Overall, I felt like I had no control over SIMPLE things that
any coder would want control over.  ShaderCL lets you make your
own functions that get run on the GPU; so, they're it's easy to
setup, fast, and provides a bunch of customization! 

(c) Applications & Speed
------------------------

Currently, the main purpose of this library is to perform image
processing functions.  However, the range of applications is left
for the user to explore!  Some possible examples are provided in
the "examples" package.  Beyond the examples provided, I am quite
confident that you can use ShaderCL for the following:

* Matrix Multiplication (for 24-bit values, sorry, no Alpha channel)
* Compressive Sensing
* Hough Transformation
* Circle Detection
* Localization & Mapping
* Path Finding

Note the roots in robotic-related algorithms.  If you ever have
a weak processor with any form of integrated graphics or OpenGL ES
support, then I'd recommend giving this library a shot!  Definitely
don't send your robot to space though - this software hasn't been
stress tested yet!

As for speed, I haven't been good about creating benchmarks because
I'm a tad too excited to get the first version out.  However, I will
provide some speed metrics for various programs on my device.

```
CPU: Intel(R) Core(TM i7-3610QM @ 2.30GHz
GPU: NVIDIA GEFORCE GTX 660M 2GB RAM
----------------------------------------
* 512x512 image : kernel 3x3 blur   --- 16ms
* 512x512 image : kernel 11x11 blur --- 16ms
* 512x512 image : kernel 21x21 blur --- 19ms
* 512x512 image : kernel 31x31 blur --- 33ms
* 512x512 image : kernel 41x41 blur --- 55ms

* 512x512   image : kernel 3x3 sobel --- 13ms
* 1024x1024 image : kernel 3x3 sobel --- 52ms
* 2048x2048 image : kernel 3x3 sobel --- 207ms

* 640x480 image : conway's game of life (1 step)     --- 15ms
* 640x480 image : conway's game of life (10 steps)   --- 16ms
* 640x480 image : conway's game of life (100 steps)  --- 37ms
* 640x480 image : conway's game of life (1000 steps) --- 197ms
```

Notice that for small kernel operations, the runtime is directly
proportional to the image size.  A quick and dirty estimate from
this data suggests that an image of size 512x512 using a kernel
that performs 1000 pixel reads per pixel requires ~20ms to run.
This is a pretty huge number of pixel reads so I ain't even mad.

[2] SETUP \ INSTALLATION
========================

Things you will need to download:

(1) Java SE Development Kit 7.0
http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html

(2) LWJGL - A Java OpenGL Bindings Library
http://www.lwjgl.org/wiki/index.php?title=Downloading_and_Setting_Up_LWJGL

(3) ShaderCL.jar & Examples
https://github.com/arikwex/ShaderCL

After downloading and installing the appropriate JDK and example files,
you will need to compile the examples using LWJGL and ShaderCL. Including
ShaderCL in your compile path is simple because it's just a simple JAR
file.  Getting LWGJL setup is only marginally more complex.  You can either
review the instructions on the LWJGL webpage provided or do the following
steps with Eclipse/manually. 

- Download LWJGL zip file from Repo or website
- Extract the file structure in to some folder (contains: jar, native, doc, res)
- Direct the compiler to use the JARs named "lwjgl.jar" and "lwjgl_util.jar"
- For each of those JARs, direct the "Native Library Location" to the appropriate
  operating system choice within the "native" directory (extracted from the zip)
  
THAT'S IT.  That's how damn easy it should be.  If you don't have to install
a new version of Java, this should take literally 90 seconds.  You can have
fast image processing code within 90 seconds of reading this! Go! Go now!
Make it happen!


[3] JAVA PROTOCOL
=================

(a) Core Operation
------------------
You could just go play with the examples because they'll give you the
true hands on experience you need...  However, I'm including this section
for completeness.

For running a single filter:
```
FilterOp op = new FilterOp("blur");
op.apply(bufferedImg);
BufferedImage result = FilterOp.getImage(); 
```

For running consecutive filters:
```
FilterOp op  = new FilterOp("blur");
FilterOp op2 = new FilterOp("edge");
op.apply(bufferedImg);
op2.apply();
BufferedImage result = FilterOp.getImage(); 
```

Notice that for the consecutive operations, you don't have to reload
the image to the GPU buffer.  Let's take a look at what the computer
is actually doing:

```
op.apply(img);
<----- convert input format ---> ~10ms for 640x480
<----- load image to GPU ------> ~2ms for 640x480
<----- apply filter -----------> ~100us	for small kernels

op2.apply();
<----- apply filter -----------> ~100us

FilterOp.getImage();
<----- read image from GPU-----> ~2ms for 640x480
```

As you can see, the image conversion time is actually the current
speed bottleneck.  This could probably be fixed by writing an elegant
C++ converter function and interfacing with it via JNI.  The correct
way to use this library is to load the input image once, and then
run many consecutive filters on that image within the GPU before
pulling the result.  The difference between apply(img) and apply() is
that apply(img) first uploads an image to the GPU which is expensive,
and apply() directly operates on the current GPU image content.  Use
apply() whenever possible!

For advanced users, FilterOp actually gives you fine control over
the 3 operations of apply(img).  Since the image conversion step is
a possible bottleneck, you can run it in parallel with other code
if you know what you're doing.  The apply(img) method really calls:

```
imagePreloader(img);	//convert image to proper byte alignment
pushLoadToSRC();		//push preloaded image to GPU texture
apply();				//run shader & render
``` 

The actual library code is quite short, so you shouldn't be afraid
to read through it, repair it, or ask me questions :)

(b) Parameter Passing
---------------------

Sometimes, you will want to create kernels that can accept various
inputs.  In the context of a blur kernel, you might want to control
the kernel size during RUNTIME without recompiling a brand new
shader.  Well, lucky for us all, OpenGL shaders already support this
sort of functionality and I just gave you access to it.

```
FilterOp op = new FilterOp("some_filter");	//load the shader AKA kernel
op.setInt("kernel_size",20);				//set the kernel_size INTEGER in the shader
op.setFloat("air_drag",-34.65);				//set the air_drag FLOAT in the shader
op.setFloatArray("points",array);			//set the points FLOAT ARRAY in the shader
```

If you issue any of these commands and get the error:
```
In Shader [<name>] variable <variable name> does not exist! (Or is not used in therefore the compiler has optimized it away)
```
then one of two things has gone wrong.
* You are trying to access a non-existent variable in a particular shader.
* Your shader doesn't use that variable and the compiler didn't bother allocating memory for it.
Figure out which of those two situations applies to you.

[4] SHADER UTILIZATION
======================

(a) Inputs and Outputs
----------------------
Welcome to the world of shaders!  Shaders are little programs that
get tossed on to your GPU cores in parallel to process the resulting
color of rendered images (typically for 3D games).  With that in mind,
you want to make sure that you realize the three things to remember
while writing shaders for ShaderCL:
(1) Use as few texture samples as necessary, they're expensive.
(2) Shader programs execute in parallel on common hardware.  Make them short so they share the GPU efficiently.
(3) Shader programs are PER-PIXEL and can only set THEMSELVES.

Let's write a basic gray-scale image converter and analyze it.  That should
be a good starting point.
```
varying float x;		// the x coordinate of this pixel [0-1]
varying float y;		// the y coordinate of this pixel [0-1]
uniform float width;	// the width of the image being processed (e.g. 640)
uniform float height;	// the height of the image being processed (e.g. 480)
uniform sampler2D txtr;	// the reference to the texture/image to process

void main() {									// the entry point for any per pixel program
	vec4 color = texture(txtr,vec2(x,y),0.0);	// grab the image color at my coordinate
	float gray = (color.x+color.y+color.z)/3.0;	// average the color of the original color
	gl_FragColor = vec4(gray,gray,gray,1);		// return the grayscale color
}
```

More sophisticated programs can use adjacent pixel information to help
compute their own output value:
```
varying float x;
varying float y;
uniform float width;
uniform float height;
uniform sampler2D txtr;

void main() {
	vec4 color = texture(txtr,vec2(x-0.5,y-0.5),0.0);	// grab some other image color at my coordinate
	gl_FragColor = vec4(color.xyz,1);					// return the grabbed color
}
```

That program is silly.  It grabs a pixel from the original image that is
(-width/2,-height/2) pixels away from its current location.  This acts in
effect as a translation.  The width and height parameters are passed in
for you so you can use the values 1.0/width or 1.0/height to shift EXACT
pixels away.  You see, the image space and texture space are linearly
mapped, so you need these reciprocal value to properly read relative pixel
locations when you are in a shader.

```
Image Space			Texture Space
+----------+ 4		0,0 -------+
|          | 8       |         |
|          | 0       |         |
|  640px   | p       |         |
+----------+ x       +--------1,1
```

If you want to pass in any runtime addressable constants for your shaders
to all share for a particular filter application, you need to define the
variable in the shader and set it from Java.

```
/* Shader */
varying float x;
varying float y;
uniform float width;
uniform float height;
uniform sampler2D txtr;
uniform float K = 1.0; 	// default K = 1.0, controllable from Java

void main() {									
	vec4 color = texture(txtr,vec2(x,y),0.0);
	float gray = (color.x+color.y+color.z)/3.0;
	gl_FragColor = vec4(gray*K,gray*K,gray*K,1); // return the grayscale color TIMES K
}

/* Java */
FilterOp op = new FilterOp("my_shader");
op.setFloat("K",0.5);
op.apply(img);
BufferedImage result = FilterOp.getImage();
```

Now, if you've already been messing around with filters, you'll have noticed
something very important: INPUT COLORS AND OUTPUT COLORS ARE BGR FORMAT.  Yes,
that's right, BGR: Blue, Green, Red.  This means that color.xyz returns a
3-vector of (B,G,R).  It also means that you should return colors in that order.
Keep that in mind, and you're good to go.

(b) Built-in functions
----------------------
These are not up to me at all.  The most complete specification for the
available built-in functions for shaders can be found on this cheat sheet:
http://www.khronos.org/opengles/sdk/docs/reference_cards/OpenGL-ES-2_0-Reference-card.pdf

Here are some of the useful functions if you're too lazy to read:
* length(vector)
* float(cast_value)
* int(cast_value)
* cos(angle)
* sin(angle)
* pow(base,exp)

[5] POSSIBLE ERRORS
===================

(a) Shaders Fail to Compile
---------------------------
If you are running on a machine that only supports the most "pure" form of OpenGL (AKA
OpenGL ES), then you will most likely see this error.  To fix it, refer to section
4.b and go to the khronos.org URL.  All of your shaders must conform to those standards.
If your shader fails to compile, it should spit out an error in console telling you what
went wrong.  These are pretty easy to fix if you just read the output + the OpenGL ES
cheat sheet.

(b) GPU Crash
-------------
I've seen this happen in two circumstances:
(1) You write a shader program that runs for too long
(2) You input an image of unacceptable dimensions

In case 1, the hardware seems to have a built in watch-dog timer that will terminate
the GPU violently if it detects an "infinite" loop.  I put infinite in quotes because
the timer will probably kill your GPU before reaching infinite time...  This isn't
a bad thing when it happens.  Your screen will go black for a second and then an error
message will pop up.  Maybe your browser will crash as well, but it's all good.

In case 2, I don't have a brilliant answer for you.  If you put in an image that is
enormous, I think it's fair to assumer that a crash would come from creating a buffer
of an illegal size.  That's probably not something you will see often unless you're
processing 100 HD images at once.  I have had times when I input an image of size
648x471 or something, and the program exits randomly.  It either happens instantly
or doesn't no happen at all.  I haven't seen this bug in a long time, but I never actively
did anything to cure it, so it may still be in there somewhere!
