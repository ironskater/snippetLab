# Plotting 2 axes in a figure

import matplotlib.pyplot as plt
import numpy as np

x = np.arange(0, np.pi * 2, 0.05)
y1 = np.sin(x)
y2 = np.tan(x + np.pi)


# ===== Using add_axes to plot multiple axes =====

# matplotlib.figure module contains the Figure class. It is a top-level container for all plot elements.
# The Figure object is instantiated by calling the figure() function from the pyplot module
fig = plt.figure()

# The add_axes() method returns the axes object and  requires a list object of 4 elements corresponding to left, bottom, width and height of the figure.
# Each number must be between 0 and 1
# A given figure can contain many Axes, but a given Axes object can only be in one Figure.
ax = fig.add_axes([0.05,0.05,0.9,0.4])
ax2 = fig.add_axes([0.05,0.55,0.9,0.4])
ax.set_title("sine wave")
ax.set_xlabel('angle')
ax.set_ylabel('sine')
ax.plot(x, y1)

ax2.set_title("tan wave")
ax2.set_xlabel('angle')
ax2.set_ylabel('tangent')
ax2.plot(x, y2)

plt.show()

# ===== Using add_subplot to plot multiple axes =====
# The subplot() function returns the axes object at a given grid position.
# The Call signature of this function is:
# 	plt.subplot(nrows, ncols, index)
# If nrows, ncols and index are all less than 10. The indexes can also be given as single, concatenated, threedigitnumber.
# Indexes go from 1 to nrows * ncols, incrementing in row-major order.

fig = plt.figure()

# now create a subplot which represents the top plot of a grid with 2 rows and 1 column.
ax1 = fig.add_subplot(2, 1, 1)
ax1.plot(x, y1)
ax1.set_title("sine wave")

# create axes2
ax2 = fig.add_subplot(212, facecolor = 'yellow')
ax2.plot(x, y2)
ax2.set_title("tan wave")

plt.show()


# ===== Using plt.subplots to plot multiple axes =====
# plt.subplots returns a figure object and a tuple containing axes objects equal to nrows*ncols.
# Each axes object is accessible by its index.
fig,a =  plt.subplots(nrows = 2, ncols = 2)

a[0][0].plot(x, x*x)
a[0][0].set_title('square')

a[0][1].plot(x,np.sqrt(x))
a[0][1].set_title('square root')

a[1][0].plot(x,np.exp(x))
a[1][0].set_title('exp')

a[1][1].plot(x,np.log10(x))
a[1][1].set_title('log')

plt.show()
