class DictDemo:
	def __init__(self,key,value):
		self.dict = {}
		self.dict[key] = value

	def __getitem__(self,key):
		return self.dict[key]

	def __setitem__(self,key,value):
		self.dict[key] = value

dict = DictDemo('k0','v0')
print("key[k0], valeu[{}]".format(dict['k0']))
dict['k1'] = 'v1'
print("key[k1], valeu[{}]".format(dict['k1']))

class ListDemo:
	def __init__(self,element):
		self.list = []
		self.list.append(element)

	def __getitem__(self, idx):
		return self.list[idx]

	def __setitem__(self,idx,element):
		self.list.append(element)

list = ListDemo("hello")
list[1] = 'world'

for idx, element in enumerate(list):
	print("list element[{}] in idx[{}]".format(list[idx], idx))
