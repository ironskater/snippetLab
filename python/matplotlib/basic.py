import numpy as np
import matplotlib.pyplot as plt

# -------------- 資料 ------------------------
x = np.arange(start = 0, stop = 10, step = 0.1)
y_sin = np.sin(x)
y_cos = np.cos(x)
# -------------- 資料 ------------------------

# -------------- 製圖 ------------------------
plt.plot(x, y_sin, 'b*') # b*：以藍色*號組成的線條
plt.plot(x, y_cos, 'r*')
plt.show()
# -------------- 製圖 ------------------------


# -------------- 文字說明 ---------------------
plt.xlabel('x point')
plt.ylabel('y_sin point')
# plt.xlim((0, 5))
plt.title('plotzzzzzzz')

# 標注資料
plt.annotate(	'Start from here', # 標注說明
				xy = (0, np.sin(0)), # 欲標注的資料點
				xytext = (1, np.sin(0) - 0.5), # 說明文字的起始點
				arrowprops = dict(facecolor = 'black'))

# 加入圖表說明，並透過loc與prop設定說明的位置與字體大小
plt.legend(	labels = ['Sine Wave', 'Cosine Wave'],
			loc = 'upper center',
			prop={'size':12})
# -------------- 文字說明 ---------------------
