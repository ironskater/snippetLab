# Plotting 2 axes in a figure

import matplotlib.pyplot as plt
import numpy as np

x = np.arange(0, np.pi * 2, 0.05)
y1 = np.sin(x)
y2 = np.tan(x + np.pi)

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
