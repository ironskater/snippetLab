import matplotlib.pyplot as plt
from matplotlib.ticker import (AutoMinorLocator, MultipleLocator)
import numpy as np
from typing import Tuple

def f(x):
	return x**2

def add_specific_annotation(ref_axes, names:Tuple, values:Tuple, positions:Tuple):
	text = "{}={:.3f}, {}={:.3f}".format(names[0], values[0], names[1], values[1])

	bbox_props = dict(boxstyle="square,pad=0.3", fc="w", ec="k", lw=0)
	arrowprops = dict(arrowstyle="->")

	kw = dict(xycoords='data',textcoords="axes fraction", arrowprops=arrowprops, bbox=bbox_props)

	ref_axes.annotate(text, xy = (values[0], values[1]), xytext=(positions[0],positions[1]), **kw)

def add_whole_annotation(ref_axes, x_axis:Tuple, y_axis:Tuple):
	for idx in range(len(x_axis)):
		ref_axes.annotate(str(y_axis[idx]), xy = (x_axis[idx], y_axis[idx]), xytext = (x_axis[idx] + 0.1, y_axis[idx] + 0.1))

x = np.arange(1,11)

# ======================= add_specific_annotation =============================

fig, axes = plt.subplots(1, 1, figsize = (12,4))

axes.plot(x, f(x), 'go-', lw = 2)

add_specific_annotation(axes, ('xname', 'yname'), (5, f(5)), (0.3, 0.5))

axes.set_title('default annotation')

plt.show()

# ======================= add_whole_annotation =============================

_, axes1 = plt.subplots(1, 1, figsize = (12,4))

axes1.plot(x, f(x), 'go-', lw = 2)

add_whole_annotation(axes1, tuple(x), tuple(f(x)))

plt.show()