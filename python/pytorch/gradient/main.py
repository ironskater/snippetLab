import torch
import numpy as np

# ============= simple derivative ==========================
# Consider the function  𝑓(𝑥)=(𝑥−2)^2 .
# compute f'(1)
def f(x):
	return (x - 2)**2

def f_prime(x):
	return 2 * (x - 2)

x = torch.tensor([1.0], requires_grad = True)

y = f(x)
y.backward(retain_graph=True)

print('Expected f\'(x):', f_prime(x))
print('Actual PyTorch\'s f\'(x):', x.grad)
# how to calculate high-order derivative?
print()

# ============= gradient computation =======================
# Let 𝑤=[𝑤1,𝑤2]𝑇
# Consider 𝑔(𝑤)=2𝑤1𝑤2+𝑤2cos(𝑤1)
# Q: Compute ∇𝑤𝑔(𝑤) and verify  ∇𝑤𝑔([𝜋,1])=[2,𝜋−1]𝑇

def g(w:torch.tensor) -> torch.tensor:
	return 2*w[0]*w[1] + w[1]*torch.cos(w[0])

def grad_g(w:torch.tensor) -> torch.tensor:
	return torch.tensor([2*w[1] - w[1]*torch.sin(w[0]), 2*w[0] + torch.cos(w[0])])

w = torch.tensor([np.pi, 1], requires_grad=True)

z = g(w)
z.backward()

print('Expected grad g(w)', grad_g(w))
print('Actual PyTorch\'s grad g(w)', w.grad)

# ============= gradient descent ============================
x = torch.tensor([5.0], requires_grad=True)
learning_rate = 0.25
epoch = 15

print('iter,\tx,\tf(x),\tf\'(x),\tf\'(x) pytorch')
for i in range(epoch):
	y = f(x)
	y.backward() # compute the gradient

	print('{},\t{:.3f},\t{:.3f},\t{:.3f},\t{:.3f}'.format(i, x.item(), f(x).item(), f_prime(x).item(), x.grad.item()))

	x.data = x.data - learning_rate * x.grad # perform a GD update step

	# We need to zero the grad variable since the backward()
	# call accumulates the gradients in .grad instead of overwriting.
	# The detach_() is for efficiency. You do not need to worry too much about it.
	x.grad.detach_()
	x.grad.zero_()