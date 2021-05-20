from typing import List
from matplotlib import pyplot as plt
# %matplotlib inline
# %matplotlib qt <- create img outside notebook

plt.figure(figsize=(4, 3)) # change size, maybe only works in notebook

def plot_xy_line(title:str, x_label:str, y_label:str, x:List, y:List, labels:List):
	for idx in range(len(y)):
		x_axis = []

		if (x == None) or (x == []):
			x_axis = [x for x in range(len(y[idx]))]
		else:
			x_axis = x[idx]

		plt.plot(x_axis, y[idx], label = labels[idx])

	plt.xlabel(x_label)
	plt.ylabel(y_label)
	plt.title(title)
	plt.legend()
	plt.show()

# plot_xy_line('TITLE', 'X_AXIS', 'Y_AXIS', [], [[2, 2, 2, 3]], ['a', 'b'])
# plot_xy_line('TITLE', 'X_AXIS', 'Y_AXIS', [], [[2, 2, 2, 3], [7, 4, 2, 1]], ['a', 'b'])

ticks = [x+2 for x in range(4)]
plot_xy_line('TITLE', 'X_AXIS', 'Y_AXIS', [ticks, ticks], [[2, 2, 2, 2], [5, 5, 5, 5]], ['a', 'b'])
