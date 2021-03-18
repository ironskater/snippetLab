# Plotting multiple lines in a axes

import matplotlib.pyplot as plt
import numpy as np

y = [1, 4, 9, 16, 25,36,49, 64]
x1 = [1, 16, 30, 42,55, 68, 77,88]
x2 = [1,6,12,18,28, 40, 52, 65]

fig = plt.figure()
ax = fig.add_axes([0.05, 0.05, 0.9, 0.9])

ax.plot(x1, y, 'bs-') # ys- means yellow colour, solid line, and square marker
ax.plot(x2, y, 'go--') # go-- means green colour, circle marker, and dash line

# legend placed at lower right
ax.legend(labels = ('tv', 'Smartphone'), loc = 'lower right')

plt.show()
