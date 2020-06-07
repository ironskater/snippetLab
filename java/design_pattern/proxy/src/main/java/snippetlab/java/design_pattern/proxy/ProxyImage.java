package snippetlab.java.design_pattern.proxy;

public final class ProxyImage implements IImage
{
	// The actual instance
	private RealImage realImage;

	private String fileName;

	public ProxyImage(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void display() {
		if(realImage == null) {
			realImage = new RealImage(this.fileName);
		}

		realImage.display();
	}
}