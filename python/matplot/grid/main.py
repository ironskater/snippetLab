import matplotlib.pyplot as plt
from matplotlib.ticker import (AutoMinorLocator, MultipleLocator)
import numpy as np
from typing import Tuple

def add_grid(ref_axes, xlimit:Tuple, ylimit:Tuple, xticks:Tuple, yticks:Tuple):

	ref_axes.set_xlim(xlimit[0], xlimit[1])
	ref_axes.set_ylim(ylimit[0], ylimit[1])

	ref_axes.xaxis.set_major_locator(MultipleLocator(xticks[0])) # 將主刻度設定為xticks[0]的倍數
	ref_axes.xaxis.set_minor_locator(AutoMinorLocator(xticks[1])) # 將主刻度切割為xticks[1]等分

	ref_axes.yaxis.set_major_locator(MultipleLocator(yticks[0]))
	ref_axes.yaxis.set_minor_locator(AutoMinorLocator(yticks[1]))

	ref_axes.grid(which='minor', linestyle='--')
	ref_axes.grid(which='major', linestyle='-', lw = 1.5)

x = np.arange(1,11)

fig, axes = plt.subplots(1, 1, figsize = (12,4))

axes.plot(x, x**2, 'go-', lw = 2)

add_grid(axes, (0, 20), (0, 150), (10, 10), (30, 3))

axes.set_title('default grid')

plt.show()