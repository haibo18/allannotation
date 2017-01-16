package cn.qm.typesafe;

import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {
	private final Gson context;
	private final TypeAdapter<T> delegate;
	private final Type type;

	TypeAdapterRuntimeTypeWrapper(Gson context, TypeAdapter<T> delegate, Type type) {
		this.context = context;
		this.delegate = delegate;
		this.type = type;
	}

	public T read(JsonReader in) throws IOException {
		return this.delegate.read(in);
	}

	public void write(JsonWriter out, T value) throws IOException {
		TypeAdapter chosen = this.delegate;
		Type runtimeType = getRuntimeTypeIfMoreSpecific(this.type, value);
		if (runtimeType != this.type) {
			TypeAdapter runtimeTypeAdapter = this.context.getAdapter(TypeToken.get(runtimeType));
			if (!(runtimeTypeAdapter instanceof Adapter)) {
				chosen = runtimeTypeAdapter;
			} else if (this.delegate instanceof Adapter) {
				chosen = runtimeTypeAdapter;
			} else {
				chosen = this.delegate;
			}
		}
		chosen.write(out, value);
	}

	private Type getRuntimeTypeIfMoreSpecific(Type type, Object value) {
		if (value != null) {
			return (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class)) ? value.getClass() : type;
		} else {
			return type;
		}
	}
}